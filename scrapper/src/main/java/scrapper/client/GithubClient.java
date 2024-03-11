package scrapper.client;

import scrapper.client.dto.GithubServiceUnitResponse;

public interface GithubClient {
    GithubServiceUnitResponse[] getRepositoryInfo(String ownerAndRepo);
}
