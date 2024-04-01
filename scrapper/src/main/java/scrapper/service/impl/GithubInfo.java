package scrapper.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.tinkoff.dto.LinkParserServiceResponse;
import ru.tinkoff.utils.ServiceNames;
import scrapper.client.GithubClient;
import scrapper.client.dto.GithubServiceUnitResponse;
import scrapper.controllers.errors.ClientException;
import scrapper.service.GetInfoFromApiService;
import scrapper.service.dto.LastUpdatedDTO;

@Service
@AllArgsConstructor
public class GithubInfo implements GetInfoFromApiService {

    private final GithubClient githubClient;
    private final ServiceNames serviceName = ServiceNames.GITHUB;

    @Override
    public LastUpdatedDTO getInfo(LinkParserServiceResponse meta) {
        if (!meta.service().equals(serviceName)) {
            throw new IllegalArgumentException("Not that service");
        }

        GithubServiceUnitResponse[] repositoryInfo;

        try {
            repositoryInfo = githubClient.getRepositoryInfo(meta.value());
        } catch (Throwable ex) {
            throw new IllegalStateException("Request went wrong: " + ex.getMessage());
        }

        if (repositoryInfo.length == 0) {
            throw new IllegalStateException("Nothing to check. Empty response from API");
        }
        GithubServiceUnitResponse lastUnitInfo = repositoryInfo[0];

        return LastUpdatedDTO.builder()
                .metaInfo(lastUnitInfo.type)
                .updatedAt(lastUnitInfo.created_at)
                .build();
    }
}
