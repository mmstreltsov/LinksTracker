package scrapper.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import scrapper.client.botClient.BotClient;
import scrapper.model.ChatStorageService;
import scrapper.model.LinkStorageService;
import scrapper.service.GetResponseFromAnyHost;

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


    private UpdateAndSendLinkServiceImpl updateAndSendLinkService;

    @BeforeEach
    void init() {
        updateAndSendLinkService = new UpdateAndSendLinkServiceImpl(checkInfo, botClient,
                linkStorageService, chatStorageService, executorServiceForFutureTasks);
    }
}