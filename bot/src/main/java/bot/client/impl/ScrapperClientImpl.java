package bot.client.impl;

import bot.client.ScrapperClient;
import bot.client.dto.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;

import java.net.URI;
import java.util.Objects;

import static org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import static org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

@Service
@AllArgsConstructor
public class ScrapperClientImpl implements ScrapperClient {

    private WebClient webClient;
    private final String tgChatIdHeader = "Tg-Chat-Id";


    @Override
    public void registerAccount(Long id) {
        var response = webClient.post()
                .uri("/tg-chat/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r -> r.bodyToMono(ApiErrorResponse.class))
                .toEntity(Object.class)
                .block();

        handleBadResponse(response);
    }

    @Override
    public void removeAccount(Long id) {
        var response = webClient.delete()
                .uri("/tg-chat/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r -> r.bodyToMono(ApiErrorResponse.class))
                .toEntity(Object.class)
                .block();

        handleBadResponse(response);
    }

    @Override
    public ListLinksResponse getTrackedLinks(Long id) {
        var response = webClient.get()
                .uri("/links")
                .header(tgChatIdHeader, String.valueOf(id))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r -> r.bodyToMono(ApiErrorResponse.class))
                .toEntity(ListLinksResponse.class)
                .block();

        handleBadResponse(response);

        return response.getBody();
    }

    @Override
    public LinkResponse addLink(Long id, URI link) {
        AddLinkRequest addLinkRequest = AddLinkRequest.builder()
                .link(link)
                .build();

        var response = webClient.post()
                .uri("/links")
                .header(tgChatIdHeader, String.valueOf(id))
                .bodyValue(addLinkRequest)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r -> r.bodyToMono(ApiErrorResponse.class))
                .toEntity(LinkResponse.class)
                .block();

        handleBadResponse(response);

        return response.getBody();
    }

    /*
    Почему WebClient при delete методе не может передавать тело запроса... Пока что костыльно перенес на другую ручку с Post-запросом
     */
    @Override
    public LinkResponse removeLink(Long id, URI link) {
        RemoveLinkRequest removeLinkRequest = RemoveLinkRequest.builder()
                .link(link)
                .build();

        var response = webClient.post()
                .uri("links/delete")
                .header(tgChatIdHeader, String.valueOf(id))
                .bodyValue(removeLinkRequest)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r -> r.bodyToMono(ApiErrorResponse.class))
                .toEntity(LinkResponse.class)
                .block();

        handleBadResponse(response);

        return response.getBody();
    }

    @Builder
    private record MakeRequest(@NotNull String path, Object[] pathVariables, String[] headerKeys,
                               String[] headerValues, Object bodyValue, @NotNull Class<?> responseClass) {
        public MakeRequest {
            if (pathVariables == null) {
                pathVariables = new Object[]{};
            }
            if (Objects.isNull(path) || Objects.isNull(responseClass)) {
                throw new IllegalArgumentException("this fields can not be null:  path, response class");
            }
        }
    }

    private ResponseEntity<?> makePostRequest(MakeRequest makeRequest) {
        RequestBodySpec part1 = webClient.post()
                .uri(makeRequest.path, makeRequest.pathVariables)
                .accept(MediaType.APPLICATION_JSON);

        for (int i = 0; i < makeRequest.headerKeys.length; ++i) {
            part1 = part1.header(makeRequest.headerKeys[i], makeRequest.headerValues[i]);
        }

        ResponseSpec retrieve;
        if (makeRequest.bodyValue != null) {
            retrieve = part1.bodyValue(makeRequest.bodyValue).retrieve();
        } else {
            retrieve = part1.retrieve();
        }

        return retrieve.onStatus(HttpStatusCode::is4xxClientError, r -> r.bodyToMono(ApiErrorResponse.class))
                .toEntity(makeRequest.responseClass)
                .block();
    }

    private ResponseEntity<?> makeGetRequest(MakeRequest makeRequest) {
        return makeGetOrDeleteRequest(webClient.get(), makeRequest);
    }

    private ResponseEntity<?> makeDeleteRequest(MakeRequest makeRequest) {
        return makeGetOrDeleteRequest(webClient.delete(), makeRequest);
    }

    private ResponseEntity<?> makeGetOrDeleteRequest(RequestHeadersUriSpec<?> requestHeadersUriSpec, MakeRequest makeRequest) {
        RequestHeadersSpec<?> part1 = requestHeadersUriSpec
                .uri(makeRequest.path, makeRequest.pathVariables)
                .accept(MediaType.APPLICATION_JSON);

        for (int i = 0; i < makeRequest.headerKeys.length; ++i) {
            part1 = part1.header(makeRequest.headerKeys[i], makeRequest.headerValues[i]);
        }

        return part1
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r -> r.bodyToMono(ApiErrorResponse.class))
                .toEntity(makeRequest.responseClass)
                .block();
    }

    private void handleBadResponse(ResponseEntity<?> response) {
        handleBadResponse(response, new IllegalStateException("Invalid request"));
    }

    private void handleBadResponse(ResponseEntity<?> response, RuntimeException ex) {
        if (response == null || !response.getStatusCode().is2xxSuccessful()) {
            throw ex;
        }
    }
}
