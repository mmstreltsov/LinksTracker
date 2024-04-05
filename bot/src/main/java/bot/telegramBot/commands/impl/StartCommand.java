package bot.telegramBot.commands.impl;

import bot.client.ScrapperClient;
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
public class StartCommand implements Command {

    private ScrapperClient scrapperClient;

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

        scrapperClient.registerAccount(id);

        return BotResponse.USER_ADDED_AND_U_R_WELCOME.msg;
    }

}
