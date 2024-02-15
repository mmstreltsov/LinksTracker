package scrapper;

import configuration.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import ru.tinkoff.configuration.LinkParserConfiguration;
import ru.tinkoff.service.LinkParserService;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
@Import(LinkParserConfiguration.class)
public class ScrapperApplication {

    @Autowired
    private LinkParserService linkParserService;

    public static void main(String[] args) {
        var ctx = SpringApplication.run(ScrapperApplication.class, args);
        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);

        System.out.println(ctx.getBean(LinkParserService.class).getResponseFromAnyHost("oijefwe"));

        System.out.println(config);
    }
}
