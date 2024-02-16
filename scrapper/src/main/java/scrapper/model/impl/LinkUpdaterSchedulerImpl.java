package scrapper.model.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import scrapper.model.LinkUpdaterScheduler;

@Slf4j
@Service
@DependsOn({"app-configuration.ApplicationConfig", "scheduler"})
public class LinkUpdaterSchedulerImpl implements LinkUpdaterScheduler {

    @Scheduled(fixedDelayString = "#{@scheduler.interval()}")
    @Override
    public void update() {
        log.info("Start updating");
        System.out.println("AHAHHAHAHA");
        log.info("Finish updating");
    }
}
