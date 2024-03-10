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
import java.util.List;

@ExtendWith(MockitoExtension.class)
class LinkUpdaterSchedulerImplTest {

    @Mock
    private GetResponseFromAnyHost checkInfo;
    @Mock
    private BotClient botClient;
    @Mock
    private LinkStorageService linkStorageService;
    @Mock
    private ChatStorageService chatStorageService;


    private LinkUpdaterSchedulerImpl linkUpdaterScheduler;

    @BeforeEach
    void init() {
        linkUpdaterScheduler = new LinkUpdaterSchedulerImpl(checkInfo, botClient, linkStorageService, chatStorageService);

        Mockito.when(linkStorageService.findAll())
                .thenReturn(List.of(
                        new Link(1L, URI.create("ahaha"), OffsetDateTime.MIN, OffsetDateTime.now()),
                        new Link(2L, URI.create("what"), OffsetDateTime.MAX, OffsetDateTime.now()),
                        new Link(3L, URI.create("ohoho"), OffsetDateTime.now(), OffsetDateTime.now())
                        ));
    }

    @Test
    void actionByUpdate_testWhenCheckInfoThrowSmth() {
        Mockito.when(checkInfo.getResponse(Mockito.any()))
                .thenThrow(RuntimeException.class);

        Assertions.assertDoesNotThrow(() -> linkUpdaterScheduler.update());
    }
}