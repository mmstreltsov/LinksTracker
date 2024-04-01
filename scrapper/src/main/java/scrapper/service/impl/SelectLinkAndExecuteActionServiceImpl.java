package scrapper.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import scrapper.model.LinkStorageService;
import scrapper.model.dto.LinkDTO;
import scrapper.service.SelectLinkAndExecuteActionService;
import scrapper.service.UpdateAndSendLinkService;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;


@Slf4j
@Service
public class SelectLinkAndExecuteActionServiceImpl implements SelectLinkAndExecuteActionService {
    private final LinkStorageService linkStorageService;
    private final UpdateAndSendLinkService updateAndSendLinkService;
    private final ExecutorService executorServiceForLinkHandle;
    private final Duration waitingTimeToRepeatSameRequest = Duration.ofMinutes(5);

    public SelectLinkAndExecuteActionServiceImpl(LinkStorageService linkStorageService, UpdateAndSendLinkService updateAndSendLinkService,
                                                 @Qualifier("executorServiceForLinkHandle") ExecutorService executorServiceForLinkHandle) {
        this.linkStorageService = linkStorageService;
        this.updateAndSendLinkService = updateAndSendLinkService;
        this.executorServiceForLinkHandle = executorServiceForLinkHandle;
    }

    @Override
    public void execute() {
        filteredLinks().forEach(
                it -> executorServiceForLinkHandle.submit(() -> updateAndSendLinkService.handle(it))
        );
    }

    private List<LinkDTO> filteredLinks() {
        return linkStorageService.findLinksCheckedFieldLessThenGivenAndUniqueUrl(
                        OffsetDateTime.now().minus(waitingTimeToRepeatSameRequest)
                );
    }
}


