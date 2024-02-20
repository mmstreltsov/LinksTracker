package bot.telegramBot.commands.impl;

import bot.telegramBot.commands.Command;
import bot.telegramBot.commands.CommandInfo;
import bot.telegramBot.utils.BotResponse;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {
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
        Long id = update.message().chat().id();
        return BotResponse.NO_TRACKED_LINKS.msg;
    }
}
