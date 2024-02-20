package bot.telegramBot.commands.impl;

import bot.telegramBot.commands.Command;
import bot.telegramBot.commands.CommandInfo;
import com.pengrad.telegrambot.model.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TrackCommand implements Command {
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
        String info = update.message().text().split(" ")[1];
        log.info("Get the link: " + info);

        return "AHAHHAHA";
    }

}
