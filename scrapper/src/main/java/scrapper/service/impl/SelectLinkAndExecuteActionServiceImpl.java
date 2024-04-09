package scrapper.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import scrapper.model.LinkStorageService;
import scrapper.model.dto.LinkDTO;
import scrapper.service.SelectLinkAndExecuteActionService;
import scrapper.service.UpdateAndSendLinkService;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutorService;


@Slf4j
@Service
@Transactional
public class SelectLinkAndExecuteActionServiceImpl implements SelectLinkAndExecuteActionService {
    private final LinkStorageService linkStorageService;
    private final UpdateAndSendLinkService updateAndSendLinkService;
    private final ExecutorService executorServiceForLinkHandle;
    private final WaitingTimeToRepeatSameRequest duration = new WaitingTimeToRepeatSameRequest(2, ChronoUnit.MINUTES);

    private record WaitingTimeToRepeatSameRequest(int amount, ChronoUnit unit) {
    }

    public SelectLinkAndExecuteActionServiceImpl(LinkStorageService linkStorageService,
                                                 UpdateAndSendLinkService updateAndSendLinkService,
                                                 @Qualifier("executorServiceForLinkHandle") ExecutorService executorServiceForLinkHandle) {
        this.linkStorageService = linkStorageService;
        this.updateAndSendLinkService = updateAndSendLinkService;
        this.executorServiceForLinkHandle = executorServiceForLinkHandle;
    }

    @Override
    public void execute() {
        int size = 501;
        int page = 0, totalPage;

        do {
            Pageable pageable = PageRequest.of(page, size);
            Page<LinkDTO> linksPage = linkStorageService.findUniqueUrlWhatNotCheckedForALongTime(duration.amount, duration.unit, pageable);
            if (linksPage == null) {
                return;
            }
            totalPage = linksPage.getTotalPages();

            linksPage.getContent().forEach(it -> {
                executorServiceForLinkHandle.execute(() -> updateAndSendLinkService.handle(it));
            });

            page++;
        } while (page < totalPage);
    }
}


