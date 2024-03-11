package scrapper.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tinkoff.dto.LinkParserServiceResponse;
import ru.tinkoff.utils.ServiceNames;
import scrapper.client.GithubClient;
import scrapper.client.dto.GithubServiceUnitResponse;
import scrapper.service.dto.LastUpdatedDTO;

import java.time.OffsetDateTime;

@ExtendWith(MockitoExtension.class)
class GithubInfoTest {
    @Mock
    private GithubClient githubClient;

    private GithubInfo githubInfo;

    @BeforeEach
    public void init() {
        githubInfo = new GithubInfo(githubClient);
    }

    @Test
    void getInfo_testWhenNotThatServiceName() {
        LinkParserServiceResponse response = LinkParserServiceResponse.builder()
                .service(ServiceNames.STACKOVERFLOW)
                .build();

        Assertions.assertThrows(IllegalStateException.class, () -> githubInfo.getInfo(response));
    }

    @Test
    void getInfo_testWhenClientSendEmptyValue() {
        Mockito.when(githubClient.getRepositoryInfo(Mockito.any())).thenReturn(new GithubServiceUnitResponse[]{});
        LinkParserServiceResponse response = LinkParserServiceResponse.builder()
                .service(ServiceNames.GITHUB)
                .value("null")
                .build();

        Assertions.assertThrows(IllegalArgumentException.class, () -> githubInfo.getInfo(response));
    }

    @Test
    void getInfo_testWhenClientThrowException() {
        Mockito.when(githubClient.getRepositoryInfo(Mockito.any())).thenThrow(RuntimeException.class);
        LinkParserServiceResponse response = LinkParserServiceResponse.builder()
                .service(ServiceNames.GITHUB)
                .value("null")
                .build();

        Assertions.assertThrows(IllegalArgumentException.class, () -> githubInfo.getInfo(response));
    }

    @Test
    void getInfo_testCorrectLogic() {
        Mockito.when(githubClient.getRepositoryInfo(Mockito.any()))
                .thenReturn(
                        new GithubServiceUnitResponse[]{
                                new GithubServiceUnitResponse("1", "1", OffsetDateTime.MIN),
                                new GithubServiceUnitResponse("1", "1", OffsetDateTime.now())
                        });

        LinkParserServiceResponse response = LinkParserServiceResponse.builder()
                .service(ServiceNames.GITHUB)
                .value("null")
                .build();

        LastUpdatedDTO info = githubInfo.getInfo(response);
        Assertions.assertEquals(OffsetDateTime.MIN, info.getUpdatedAt());
    }
}