package scrapper.client.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import scrapper.client.GithubClient;
import scrapper.client.dto.GithubServiceUnitResponse;

public class GitHubClientImpl implements GithubClient {

    @Value("${github.api.token}")
    private String token;

    @Bean
    private WebClient webClient() {
        return WebClient.create();
    }

    @Override
    public GithubServiceUnitResponse[] getRepositoryInfo(String ownerAndRepo) {
        WebClient webClient = webClient();
        ResponseEntity<GithubServiceUnitResponse[]> response = webClient.get()
                .uri("https://api.github.com/networks/" + ownerAndRepo + "/events")
                .header("Accept", "application/vnd.github+json")
                .header("Authorization", "Bearer " + token)
//                .header("X-GitHub-Api-Version:", "2022-11-28")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(GithubServiceUnitResponse[].class)
                .block();

        if (response == null || response.getStatusCode().is4xxClientError()) {
            throw new IllegalStateException("Github Service: invalid link");
        }
        return response.getBody();
    }
}
