package bot.telegramBot.bot;

import bot.telegramBot.commands.Command;
import bot.telegramBot.service.CommandExecService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SetMyCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class TelegramBotImpl {

    private final TelegramBot bot;

    public TelegramBotImpl(String telegramToken, List<Command> commands, CommandExecService commandExecService) {
        this.bot = new TelegramBot(telegramToken);

        setMyCommands(commands);

        bot.setUpdatesListener(updates -> {
            process(commandExecService, updates);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> {
            if (Objects.nonNull(e.response())) {
                log.info("Telegram bot response error, error code: " + e.response().errorCode());
                log.info("Telegram bot response error, description: " + e.response().description());
            } else {
                log.info("Telegram bot response error, idk what it is: " + Arrays.toString(e.getStackTrace()));
            }
        });

    }

    private void process(CommandExecService commandExecService, List<Update> updates) {
        updates.forEach(update -> {
            if (update.message() != null && update.message().text() != null) {
                bot.execute(commandExecService.execCommand(update));
            }
        });
    }

    private void setMyCommands(List<Command> commands) {
        BotCommand[] botCommands = commands.stream()
                .map(Command::toApiCommand)
                .toArray(BotCommand[]::new);
        SetMyCommands setMyCommands = new SetMyCommands(botCommands);
        bot.execute(setMyCommands);
    }
}
