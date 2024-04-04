package scrapper.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import scrapper.client.botClient.BotClient;
import scrapper.model.ChatStorageService;
import scrapper.model.LinkStorageService;
import scrapper.model.dto.ChatDTO;
import scrapper.model.dto.LinkDTO;
import scrapper.service.GetResponseFromAnyHost;
import scrapper.service.UpdateAndSendLinkService;
import scrapper.service.dto.LastUpdatedDTO;

import java.util.concurrent.ExecutorService;

@Slf4j
@Service
public class UpdateAndSendLinkServiceImpl implements UpdateAndSendLinkService {
    private final GetResponseFromAnyHost checkInfo;
    private final BotClient botClient;
    private final LinkStorageService linkStorageService;
    private final ChatStorageService chatStorageService;
    private final ExecutorService executorServiceForFutureTasks;

    public UpdateAndSendLinkServiceImpl(GetResponseFromAnyHost checkInfo,
                                        BotClient botClient,
                                        LinkStorageService linkStorageService,
                                        ChatStorageService chatStorageService,
                                        @Qualifier("executorServiceForFutureTasks") ExecutorService executorServiceForFutureTasks) {
        this.checkInfo = checkInfo;
        this.botClient = botClient;
        this.linkStorageService = linkStorageService;
        this.chatStorageService = chatStorageService;
        this.executorServiceForFutureTasks = executorServiceForFutureTasks;
    }


    public void handle(LinkDTO linkDTO) {
        log.info("Get link: " + linkDTO.getUrl());
        executorServiceForFutureTasks.submit(() -> linkStorageService.setCheckFieldToNow(linkDTO));

        LastUpdatedDTO response;
        try {
            response = checkInfo.getResponse(linkDTO.getUrl());
        } catch (Throwable ignored) {
            return;
        }
        log.info("Link parsed: " + linkDTO.getUrl());

        if (!isLinkUpdated(linkDTO, response)) {
            return;
        }

        int size = 501;
        int page = 0, totalPage;
        do {
            Pageable pageable = PageRequest.of(page, size);
            Page<ChatDTO> chats = chatStorageService.findAllChatsByCurrentUrl(linkDTO.getUrl(), pageable);
            totalPage = chats.getTotalPages();

            log.info("Get chats: " + chats.getContent());

            executorServiceForFutureTasks.submit(() -> botClient.updateLink(chats.getContent(), linkDTO));
            executorServiceForFutureTasks.submit(() -> linkStorageService.setUpdateFieldToValue(linkDTO, response.getUpdatedAt()));

            page++;
        } while (page < totalPage);
    }

    private boolean isLinkUpdated(LinkDTO linkDTO, LastUpdatedDTO lastUpdatedDTO) {
        return lastUpdatedDTO.getUpdatedAt()
                .isAfter(linkDTO.getUpdatedAt().plusSeconds(3));
    }
}
