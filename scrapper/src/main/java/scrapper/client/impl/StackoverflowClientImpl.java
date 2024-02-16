package scrapper.client.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import scrapper.client.StackoverflowClient;
import scrapper.client.dto.StackoverflowServiceResponse;
import scrapper.client.dto.StackoverflowUnitServiceResponse;

import java.util.Objects;

public class StackoverflowClientImpl implements StackoverflowClient {

    @Bean
    private WebClient webClient() {
        return WebClient.create();
    }

    @Override
    public StackoverflowUnitServiceResponse getInfo(String id) {
        WebClient webClient = webClient();
        ResponseEntity<StackoverflowServiceResponse> response = webClient.get()
                .uri("https://api.stackexchange.com/2.3/questions/" + id + "?site=stackoverflow")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(StackoverflowServiceResponse.class)
                .block();


        if (response == null
                || response.getStatusCode().is4xxClientError()
                || Objects.requireNonNull(response.getBody()).items.length == 0) {
            throw new IllegalStateException("Stackoverflow Service: invalid id");
        }

        return response.getBody().items[0];
    }
}
