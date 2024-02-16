package scrapper;

import configuration.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
@EnableScheduling
public class ScrapperApplication {

    public static void main(String[] args) {
        var ctx = SpringApplication.run(ScrapperApplication.class, args);
        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);
//        System.out.println(Arrays.toString(ctx.getBean(GithubClient.class).getRepositoryInfo("mmstreltsov/java_course_hws")));
//        System.out.println(ctx.getBean(StackoverflowClient.class).getInfo("47373889"));

        System.out.println(config);
    }
}
