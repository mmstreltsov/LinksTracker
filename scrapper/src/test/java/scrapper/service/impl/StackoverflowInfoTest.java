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
import scrapper.client.StackoverflowClient;
import scrapper.client.dto.StackoverflowUnitServiceResponse;
import scrapper.service.dto.LastUpdatedDTO;

import java.time.OffsetDateTime;

@ExtendWith(MockitoExtension.class)
class StackoverflowInfoTest {
    @Mock
    private StackoverflowClient stackoverflowClient;

    private StackoverflowInfo stackoverflowInfo;

    @BeforeEach
    public void init() {
        stackoverflowInfo = new StackoverflowInfo(stackoverflowClient);
    }

    @Test
    void getInfo_testWhenNotThatServiceName() {
        LinkParserServiceResponse response = LinkParserServiceResponse.builder()
                .service(ServiceNames.GITHUB)
                .build();

        Assertions.assertThrows(IllegalArgumentException.class, () -> stackoverflowInfo.getInfo(response));
    }

    @Test
    void getInfo_testWhenClientThrowException() {
        Mockito.when(stackoverflowClient.getInfoFromQuestion(Mockito.any())).thenThrow(RuntimeException.class);
        LinkParserServiceResponse response = LinkParserServiceResponse.builder()
                .service(ServiceNames.STACKOVERFLOW)
                .value("null")
                .build();

        Assertions.assertThrows(IllegalStateException.class, () -> stackoverflowInfo.getInfo(response));
    }

    @Test
    void getInfo_testCorrectLogic() {
        Mockito.when(stackoverflowClient.getInfoFromQuestion(Mockito.any()))
                .thenReturn(new StackoverflowUnitServiceResponse(OffsetDateTime.MIN, "1"));

        LinkParserServiceResponse response = LinkParserServiceResponse.builder()
                .service(ServiceNames.STACKOVERFLOW)
                .value("null")
                .build();

        LastUpdatedDTO info = stackoverflowInfo.getInfo(response);
        Assertions.assertEquals(OffsetDateTime.MIN, info.getUpdatedAt());
    }
}