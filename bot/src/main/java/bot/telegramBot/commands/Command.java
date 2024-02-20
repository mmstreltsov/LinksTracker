package bot.telegramBot.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;

public interface Command {

    String command();

    String description();

    String handle(Update update);

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}
