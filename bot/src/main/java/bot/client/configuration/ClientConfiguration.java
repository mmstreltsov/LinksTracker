package bot.client.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    private final String baseUrl = "http://localhost:4242";

    @Bean
    public WebClient webClient() {
        return WebClient.create(baseUrl);
    }
}
