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

@Slf4j
@Component
@AllArgsConstructor
public class TrackCommand implements Command {

    private ScrapperClient scrapperClient;

    @Override
    public String command() {
        return CommandInfo.TRACK.getCommand();
    }

    @Override
    public String description() {
        return CommandInfo.TRACK.getDescription();
    }

    @Override
    public String handle(Update update) {
        try {
            Long id = update.message().chat().id();
            URI link = UriHandler.getLinkFromMessage(update);

            LinkResponse linkResponse = scrapperClient.addLink(id, link);

            return BotResponse.LINK_SUCCESSFUL_TRACKED.msg;
        } catch (ApiErrorResponse ex) {
            log.debug("failed to exec " + command() + ": " + ex.description);
            return ex.description;
        }
    }
}
