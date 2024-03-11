package scrapper.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import scrapper.client.botClient.BotClient;
import scrapper.model.ChatStorageService;
import scrapper.model.LinkStorageService;
import scrapper.model.entity.Link;
import scrapper.service.GetResponseFromAnyHost;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.concurrent.ExecutorService;

@ExtendWith(MockitoExtension.class)
class UpdateAndSendLinkServiceImplTest {

    @Mock
    private GetResponseFromAnyHost checkInfo;
    @Mock
    private BotClient botClient;
    @Mock
    private LinkStorageService linkStorageService;
    @Mock
    private ChatStorageService chatStorageService;
    @Mock
    private ExecutorService executorServiceForFutureTasks;


    private UpdateAndSendLinkServiceImpl ahahaService;

    @BeforeEach
    void init() {
        ahahaService = new UpdateAndSendLinkServiceImpl(checkInfo, botClient,
                linkStorageService, chatStorageService, executorServiceForFutureTasks);
    }

    @Test
    void actionByUpdate_testWhenCheckInfoThrowSmth() {
        Mockito.when(checkInfo.getResponse(Mockito.any()))
                .thenThrow(RuntimeException.class);
        Link link = new Link(1L, URI.create("ahaha"), OffsetDateTime.MIN, OffsetDateTime.now());

        Assertions.assertDoesNotThrow(() -> ahahaService.handle(link));
    }
}