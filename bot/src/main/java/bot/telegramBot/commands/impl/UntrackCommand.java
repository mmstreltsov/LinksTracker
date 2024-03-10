package bot.telegramBot.commands.impl;

import bot.client.ScrapperClient;
import bot.client.dto.ApiErrorResponse;
import bot.client.dto.LinkResponse;
import bot.telegramBot.commands.Command;
import bot.telegramBot.commands.CommandInfo;
import bot.telegramBot.commands.impl.utils.UriHandler;
import bot.telegramBot.utils.BotResponse;
import com.pengrad.telegrambot.model.Update;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@Slf4j
@AllArgsConstructor
public class UntrackCommand implements Command {

    private ScrapperClient scrapperClient;

    @Override
    public String command() {
        return CommandInfo.UNTRACK.getCommand();
    }

    @Override
    public String description() {
        return CommandInfo.UNTRACK.getDescription();
    }

    @Override
    public String handle(Update update) {
        try {
            log.info("Начинаем антрэк");
            Long id = update.message().chat().id();
            URI link = UriHandler.getLinkFromMessage(update);

            log.info("Вот: " + id + ", " + link);
            LinkResponse linkResponse = scrapperClient.removeLink(id, link);
            if (linkResponse != null) {
                log.info("resp: " + linkResponse.id + " " + linkResponse.url.toString());
            } else {
                log.info("Пустой ответ");
            }

            return BotResponse.LINK_SUCCESSFUL_UNTRACKED.msg;
        } catch (ApiErrorResponse ex) {
            log.debug("failed to exec " + command() + ": " + ex.description);
            return ex.description;
        }
    }
}
