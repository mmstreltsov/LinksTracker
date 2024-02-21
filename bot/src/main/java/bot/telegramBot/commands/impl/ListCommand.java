package bot.telegramBot.commands.impl;

import bot.client.ScrapperClient;
import bot.client.dto.ApiErrorResponse;
import bot.client.dto.ListLinksResponse;
import bot.telegramBot.commands.Command;
import bot.telegramBot.commands.CommandInfo;
import bot.telegramBot.utils.BotResponse;
import com.pengrad.telegrambot.model.Update;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ListCommand implements Command {

    private ScrapperClient scrapperClient;

    @Override
    public String command() {
        return CommandInfo.LIST.getCommand();
    }

    @Override
    public String description() {
        return CommandInfo.LIST.getDescription();
    }

    @Override
    public String handle(Update update) {
        try {
            Long id = update.message().chat().id();

            ListLinksResponse links = scrapperClient.getTrackedLinks(id);
            if (links.size == null) {
                throw new RuntimeException("Server return null: maybe you are not registered");
            }
            if (links.size == 0) {
                return BotResponse.NO_TRACKED_LINKS.msg;
            }
            return links.links.toString();

        } catch (ApiErrorResponse ex) {
            log.debug("failed to exec " + command() + ": " + ex.description);
            return ex.description;
        }
    }
}
