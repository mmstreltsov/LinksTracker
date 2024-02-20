package bot.telegramBot.commands.impl;

import bot.telegramBot.commands.Command;
import bot.telegramBot.commands.CommandInfo;
import bot.telegramBot.utils.BotResponse;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {
    @Override
    public String command() {
        return CommandInfo.START.getCommand();
    }

    @Override
    public String description() {
        return CommandInfo.START.getDescription();
    }

    @Override
    public String handle(Update update) {
        Long id = update.message().chat().id();
        return BotResponse.USER_ADDED_AND_U_R_WELCOME.msg;
    }

}
