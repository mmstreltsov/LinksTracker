package bot.client.impl;

import bot.client.ScrapperClient;
import bot.client.dto.AddLinkRequest;
import bot.client.dto.ApiErrorResponse;
import bot.client.dto.LinkResponse;
import bot.client.dto.ListLinksResponse;
import bot.client.dto.RemoveLinkRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

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
                .onStatus(HttpStatusCode::is5xxServerError, r -> r.bodyToMono(ApiErrorResponse.class))
                .toEntity(Object.class)
                .block();
    }

    @Override
    public void removeAccount(Long id) {
        var response = webClient.delete()
                .uri("/tg-chat/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r -> r.bodyToMono(ApiErrorResponse.class))
                .onStatus(HttpStatusCode::is5xxServerError, r -> r.bodyToMono(ApiErrorResponse.class))
                .toEntity(Object.class)
                .block();
    }

    @Override
    public ListLinksResponse getTrackedLinks(Long id) {
        var response = webClient.get()
                .uri("/links")
                .header(tgChatIdHeader, String.valueOf(id))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r -> r.bodyToMono(ApiErrorResponse.class))
                .onStatus(HttpStatusCode::is5xxServerError, r -> r.bodyToMono(ApiErrorResponse.class))
                .toEntity(ListLinksResponse.class)
                .block();

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
                .onStatus(HttpStatusCode::is5xxServerError, r -> r.bodyToMono(ApiErrorResponse.class))
                .toEntity(LinkResponse.class)
                .block();

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
                .uri("/links/delete")
                .header(tgChatIdHeader, String.valueOf(id))
                .bodyValue(removeLinkRequest)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r -> r.bodyToMono(ApiErrorResponse.class))
                .onStatus(HttpStatusCode::is5xxServerError, r -> r.bodyToMono(ApiErrorResponse.class))
                .toEntity(LinkResponse.class)
                .block();

        return response.getBody();
    }
}
