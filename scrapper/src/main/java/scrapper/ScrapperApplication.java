package scrapper;

import configuration.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.tinkoff.configuration.LinkParserConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
@EnableScheduling
@Import({LinkParserConfiguration.class})
public class ScrapperApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScrapperApplication.class, args);
    }
}
