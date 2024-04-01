package scrapper.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tinkoff.dto.LinkParserServiceResponse;
import ru.tinkoff.service.LinkParserService;
import ru.tinkoff.utils.ServiceNames;
import scrapper.controllers.errors.ClientException;
import scrapper.service.dto.LastUpdatedDTO;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class GetResponseFromAnyHostImplTest {

    @Mock
    private GithubInfo githubInfo;
    @Mock
    private StackoverflowInfo stackoverflowInfo;

    @Mock
    private LinkParserService linkParserService;

    private GetResponseFromAnyHostImpl getResponseFromAnyHost;


    @BeforeEach
    public void init() {
        getResponseFromAnyHost = new GetResponseFromAnyHostImpl(List.of(githubInfo, stackoverflowInfo), linkParserService);
    }

    @Test
    void getResponse_whenLinkParserServiceReturnNull() {
        Mockito.when(linkParserService.getResponseFromAnyHost(Mockito.any()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ClientException.class, () -> getResponseFromAnyHost.getResponse("ahaha"));
    }

    @Test
    void getResponse_whenEveryServiceIsNotMatch() {
        Mockito.when(linkParserService.getResponseFromAnyHost(Mockito.any()))
                .thenReturn(Optional.of(new LinkParserServiceResponse(ServiceNames.GITHUB, "null")));
        Mockito.when(githubInfo.getInfo(Mockito.any())).thenThrow(RuntimeException.class);
        Mockito.when(stackoverflowInfo.getInfo(Mockito.any())).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> getResponseFromAnyHost.getResponse("ahaha"));
    }

    @Test
    void getResponse_testCorrectLogic() {
        Mockito.when(linkParserService.getResponseFromAnyHost(Mockito.any()))
                .thenReturn(Optional.of(new LinkParserServiceResponse(ServiceNames.STACKOVERFLOW, "null")));
        Mockito.when(githubInfo.getInfo(Mockito.any())).thenThrow(RuntimeException.class);
        Mockito.when(stackoverflowInfo.getInfo(Mockito.any())).thenReturn(new LastUpdatedDTO("", OffsetDateTime.MIN));

        Assertions.assertEquals(OffsetDateTime.MIN, getResponseFromAnyHost.getResponse("").getUpdatedAt());
    }

}