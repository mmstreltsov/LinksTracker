package bot.telegramBot.service.impl;

import bot.telegramBot.commands.Command;
import bot.telegramBot.service.CommandExecService;
import bot.telegramBot.service.ResponseFromCommand;
import bot.telegramBot.utils.BotResponse;
import com.pengrad.telegrambot.model.Update;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CommandExecServiceImpl implements CommandExecService {

    private List<Command> commandList;

    @Override
    public ResponseFromCommand execCommand(Update update) {
        Long id = update.message().chat().id();
        try {
            String text = update.message().text();
            String command = text.split(" ")[0];

            for (var cmd : commandList) {
                if (!command.equalsIgnoreCase(cmd.command())) {
                    continue;
                }
                String response = cmd.handle(update);

                return new ResponseFromCommand(id, response);
            }

            return new ResponseFromCommand(id, BotResponse.NOT_SUPPORTED_COMMAND.msg);

        } catch (Exception ex) {
            log.info("Bad request: id = " + id + ", error = " + ex.getMessage());
            return new ResponseFromCommand(id, BotResponse.SMTH_GONE_WRONG.msg + ":\n" + ex.getMessage());
        }
    }
}
