package scrapper.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import scrapper.service.LinkUpdaterScheduler;

@Slf4j
@AllArgsConstructor
@Service
@DependsOn({"app-configuration.ApplicationConfig", "scheduler"})
public class LinkUpdaterSchedulerImpl implements LinkUpdaterScheduler {

    private SelectLinkAndExecuteActionServiceImpl service;
    @Scheduled(fixedDelayString = "#{@scheduler.interval()}")
    @Override
    public void update() {
        log.info("Start updating");
        service.execute();
        log.info("Finish updating");
    }
}
