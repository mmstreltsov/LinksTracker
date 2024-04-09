package configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "app", ignoreInvalidFields = false)
public record ApplicationConfig(@NotNull String test,
                                @Bean @NotNull Scheduler scheduler,
                                @Bean String topicNameForMQ,
                                @Bean String queueNameForMQ,
                                @Bean String topicNameForMQ_dlq,
                                @Bean String queueNameForMQ_dlq) {

    public record Scheduler(Duration interval) {
    }
}
