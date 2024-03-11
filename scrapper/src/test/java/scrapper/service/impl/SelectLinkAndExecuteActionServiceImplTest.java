package scrapper.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import scrapper.model.LinkStorageService;
import scrapper.model.entity.Link;
import scrapper.service.UpdateAndSendLinkService;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;

@ExtendWith(MockitoExtension.class)
class SelectLinkAndExecuteActionServiceImplTest {

    @Mock
    private UpdateAndSendLinkService updateAndSendLinkService;
    @Mock
    private LinkStorageService linkStorageService;
    @Mock
    private ExecutorService executorService;

    private SelectLinkAndExecuteActionServiceImpl service;

    @BeforeEach
    void init() {
        service = new SelectLinkAndExecuteActionServiceImpl(linkStorageService, updateAndSendLinkService,
                executorService);
    }

    @Test
    void execute_testCorrectFilteredValues() {
        Mockito.when(linkStorageService.findLinksWithCheckedFieldLessThenGiven(Mockito.any()))
                .thenReturn(List.of(
                        new Link(1L, URI.create("a"), OffsetDateTime.now(), OffsetDateTime.now()),
                        new Link(1L, URI.create("ab"), OffsetDateTime.now(), OffsetDateTime.now()),
                        new Link(1L, URI.create("abc"), OffsetDateTime.now(), OffsetDateTime.now()),
                        new Link(1L, URI.create("a"), OffsetDateTime.now(), OffsetDateTime.now()),
                        new Link(1L, URI.create("a"), OffsetDateTime.now(), OffsetDateTime.now())
                ));

        service.execute();

        Mockito.verify(executorService, Mockito.times(3)).submit(Mockito.any(Runnable.class));
    }
}