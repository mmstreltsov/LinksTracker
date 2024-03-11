package scrapper.client.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import scrapper.client.GithubClient;
import scrapper.client.dto.GithubServiceUnitResponse;


@Service
class GitHubClientImpl implements GithubClient {

    @Value("${github.api.token}")
    private String token;

    private final WebClient webClient;

    GitHubClientImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public GithubServiceUnitResponse[] getRepositoryInfo(String ownerAndRepo) {
        ResponseEntity<GithubServiceUnitResponse[]> response = webClient.get()
                .uri("https://api.github.com/networks/" + ownerAndRepo + "/events")
                .header("Accept", "application/vnd.github+json")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r -> r.bodyToMono(RuntimeException.class))
                .onStatus(HttpStatusCode::is5xxServerError, r -> r.bodyToMono(RuntimeException.class))
                .toEntity(GithubServiceUnitResponse[].class)
                .block();

        if (response == null || !response.getStatusCode().is2xxSuccessful()) {
            throw new IllegalStateException("Github Service: invalid link");
        }
        return response.getBody();
    }
}
