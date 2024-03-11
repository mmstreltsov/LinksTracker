package scrapper.service.impl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ServicesConfiguration {

    @Bean
    public ExecutorService executorServiceForLinkHandle() {
        return Executors.newFixedThreadPool(4);
    }

    @Bean
    public ExecutorService executorServiceForFutureTasks() {
        return Executors.newFixedThreadPool(4);
    }
}
