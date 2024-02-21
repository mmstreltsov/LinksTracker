package bot.telegramBot.configuration;


import configuration.ApplicationConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn({"app-configuration.ApplicationConfig"})
public class TelegramBotConfiguration {

    @Bean
    public String telegramToken(ApplicationConfig applicationConfig) {
        return applicationConfig.telegramBotAPI().token();
    }
}
