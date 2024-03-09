package bot;

import bot.client.ScrapperClient;
import configuration.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication {
    public static void main(String[] args) throws Exception{
        var ctx = SpringApplication.run(BotApplication.class, args);

//
//        ScrapperClient client = ctx.getBean(ScrapperClient.class);
//
//        System.out.println(client.addLink(11L, new URI("aahahhahahah")));
    }
}
