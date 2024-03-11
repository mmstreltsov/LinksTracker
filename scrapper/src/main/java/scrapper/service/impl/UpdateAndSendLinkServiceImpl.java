package scrapper.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import scrapper.client.botClient.BotClient;
import scrapper.model.ChatStorageService;
import scrapper.model.LinkStorageService;
import scrapper.model.entity.Chat;
import scrapper.model.entity.Link;
import scrapper.service.GetResponseFromAnyHost;
import scrapper.service.UpdateAndSendLinkService;
import scrapper.service.dto.LastUpdatedDTO;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Slf4j
@Service
public class UpdateAndSendLinkServiceImpl implements UpdateAndSendLinkService {
    private final GetResponseFromAnyHost checkInfo;
    private final BotClient botClient;
    private final LinkStorageService linkStorageService;
    private final ChatStorageService chatStorageService;
    private final ExecutorService executorServiceForFutureTasks;

    public UpdateAndSendLinkServiceImpl(GetResponseFromAnyHost checkInfo, BotClient botClient,
                                        LinkStorageService linkStorageService, ChatStorageService chatStorageService,
                                        @Qualifier("executorServiceForFutureTasks") ExecutorService executorServiceForFutureTasks) {
        this.checkInfo = checkInfo;
        this.botClient = botClient;
        this.linkStorageService = linkStorageService;
        this.chatStorageService = chatStorageService;
        this.executorServiceForFutureTasks = executorServiceForFutureTasks;
    }


    public void handle(Link link) {
        log.info("Get link: " + link.getUrl().toString());
        executorServiceForFutureTasks.submit(() -> linkStorageService.setCheckFieldToNow(link));

        LastUpdatedDTO response;
        try {
            response = checkInfo.getResponse(link.getUrl().toString());
        } catch (Throwable ignored) {
            return;
        }
        log.info("Link parsed: " + link.getUrl().toString());

        if (!isLinkUpdated(link, response)) {
            return;
        }

        List<Chat> chats = chatStorageService.findAllChatsByCurrentUrl(link.getUrl().toString());
        log.info("Get chats: " + chats);
        executorServiceForFutureTasks.submit(() -> botClient.updateLink(chats, link));
        executorServiceForFutureTasks.submit(() -> linkStorageService.setUpdateFieldToNow(link));
    }

    private boolean isLinkUpdated(Link link, LastUpdatedDTO lastUpdatedDTO) {
        return lastUpdatedDTO.getUpdatedAt()
                .isAfter(link.getUpdatedAt().plusSeconds(3));
    }
}
