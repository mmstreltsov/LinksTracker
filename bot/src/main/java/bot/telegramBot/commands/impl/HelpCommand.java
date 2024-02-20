package bot.telegramBot.commands.impl;

import bot.telegramBot.commands.Command;
import bot.telegramBot.commands.CommandInfo;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    @Override
    public String command() {
        return CommandInfo.HELP.getCommand();
    }

    @Override
    public String description() {
        return CommandInfo.HELP.getDescription();
    }

    @Override
    public String handle(Update update) {
        return help();
    }

    private String help() {
        StringBuilder sb = new StringBuilder();
        sb.append("Supported commands:\n\n");
        for (var cmdInfo : CommandInfo.values()) {
            sb.append(cmdInfo.getCommand());
            sb.append(": ");
            sb.append(cmdInfo.getDescription());
            sb.append("\n");
        }
        return sb.toString();
    }
}
