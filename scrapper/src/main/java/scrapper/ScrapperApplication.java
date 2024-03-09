package scrapper;

import configuration.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import scrapper.client.GithubClient;
import scrapper.client.StackoverflowClient;
import scrapper.client.botClient.BotClient;
import scrapper.model.entity.Chat;
import scrapper.model.entity.Link;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
@EnableScheduling
public class ScrapperApplication {

    public static void main(String[] args) {
        var ctx = SpringApplication.run(ScrapperApplication.class, args);
        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);
//        System.out.println(Arrays.toString(ctx.getBean(GithubClient.class).getRepositoryInfo("mmstreltsov/java_course_hws")));
//        System.out.println(ctx.getBean(StackoverflowClient.class).getInfo("47373889"));

        ctx.getBean(BotClient.class).updateLink(List.of(new Chat(1L, 1L, 1L)), new Link(1L, URI.create("AHAHHA")));
        System.out.println(config);
    }
}
