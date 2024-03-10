package scrapper.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scrapper.client.botClient.BotClient;
import scrapper.model.ChatStorageService;
import scrapper.model.LinkStorageService;
import scrapper.model.entity.Chat;
import scrapper.model.entity.Link;
import scrapper.service.GetResponseFromAnyHost;
import scrapper.service.LinkUpdaterScheduler;
import scrapper.service.dto.LastUpdatedDTO;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Slf4j
@AllArgsConstructor
@Service
@DependsOn({"app-configuration.ApplicationConfig", "scheduler"})
public class LinkUpdaterSchedulerImpl implements LinkUpdaterScheduler {

    private GetResponseFromAnyHost checkInfo;
    private BotClient botClient;
    private LinkStorageService linkStorageService;
    private ChatStorageService chatStorageService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(8);

    private boolean isLinkUpdated(Link link, LastUpdatedDTO lastUpdatedDTO) {
        return false;
        //        return lastUpdatedDTO.getUpdatedAt()
//                .isAfter(link.getUpdatedAt().plusSeconds(3));
    }

    private Consumer<Link> action() {
        return new Consumer<>() {
            @Override
            @Transactional
            public void accept(Link link) {
                log.info("Get link: " + link.getUrl().toString());

                LastUpdatedDTO response;
                try {
                    response = checkInfo.getResponse(link.getUrl().toString());
                } catch (Throwable ignored) {
                    return;
                }
                log.info("Link parsed: " + link.getUrl().toString());

                executorService.submit(() -> linkStorageService.setCheckFieldToNow(link));
                if (!isLinkUpdated(link, response)) {
                    return;
                }

                List<Chat> chats = chatStorageService.findAllChatsByCurrentUrl(link.getUrl().toString());
                log.info("Get chats: " + chats);
                executorService.submit(() -> botClient.updateLink(chats, link));
                executorService.submit(() -> linkStorageService.setUpdateFieldToNow(link));
            }
        };
    }

    @Scheduled(fixedDelayString = "#{@scheduler.interval()}")
    @Override
    public void update() {
        log.info("Start updating");

        linkStorageService.findAll().stream()
                .parallel()
                .forEach(it -> action().accept(it));

        log.info("Finish updating");
    }
}
