package scrapper.client.botClient.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import scrapper.client.botClient.BotClient;
import scrapper.client.botClient.dto.ApiErrorResponse;
import scrapper.client.botClient.dto.LinkUpdateRequest;
import scrapper.model.dto.ChatDTO;
import scrapper.model.dto.LinkDTO;

import java.util.List;


@Service
@Slf4j
public class BotClientImpl implements BotClient {

    private final String baseUrl = "http://localhost:8080";

    private WebClient webClient() {
        return WebClient.create(baseUrl);
    }

    @Override
    public void updateLink(List<ChatDTO> chatDTO, LinkDTO linkDTO) {
        log.info("Trying to send request to Bot");
        LinkUpdateRequest linkUpdateRequest = LinkUpdateRequest.builder()
                .tgChatIds(chatDTO.stream().map(ChatDTO::getChatId).toList())
                .url(linkDTO.getUrl().toString())
                .id(-1L)
                .description("stub")
                .build();

        WebClient webClient = webClient();
        webClient.post()
                .uri("/updates")
                .bodyValue(linkUpdateRequest)
                .header("Accept", "application/vnd.github+json")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r -> r.bodyToMono(ApiErrorResponse.class))
                .onStatus(HttpStatusCode::is5xxServerError, r -> r.bodyToMono(ApiErrorResponse.class))
                .toEntity(Object.class)
                .block();
        log.info("Sent request to Bot");
    }
}
