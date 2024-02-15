package scrapper.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import scrapper.client.impl.GitHubClientImpl;
import scrapper.client.impl.StackoverflowClientImpl;

@Configuration
public class ClientConfiguration {

    @Bean
    public GithubClient githubClient() {
        return new GitHubClientImpl();
    }

    @Bean
    public StackoverflowClient stackoverflowClient() {
        return new StackoverflowClientImpl();
    }
}
