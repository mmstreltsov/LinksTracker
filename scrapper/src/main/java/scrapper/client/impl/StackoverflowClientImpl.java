package scrapper.client.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import scrapper.client.StackoverflowClient;
import scrapper.client.dto.StackoverflowServiceResponse;
import scrapper.client.dto.StackoverflowUnitServiceResponse;

import java.util.Objects;


@AllArgsConstructor
@Service
public class StackoverflowClientImpl implements StackoverflowClient {

    private final WebClient webClient;


    @Override
    public StackoverflowUnitServiceResponse getInfoFromQuestion(String id) {
        ResponseEntity<StackoverflowServiceResponse> response = webClient.get()
                .uri("https://api.stackexchange.com/2.3/questions/" + id + "?site=stackoverflow")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r -> r.bodyToMono(RuntimeException.class))
                .onStatus(HttpStatusCode::is5xxServerError, r -> r.bodyToMono(RuntimeException.class))
                .toEntity(StackoverflowServiceResponse.class)
                .block();


        if (response == null
                || !response.getStatusCode().is2xxSuccessful()
                || Objects.requireNonNull(response.getBody()).items.length == 0) {
            throw new IllegalStateException("Stackoverflow Service: invalid id");
        }

        return response.getBody().items[0];
    }
}
