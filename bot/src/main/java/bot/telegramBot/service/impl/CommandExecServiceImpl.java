package bot.telegramBot.service.impl;

import bot.telegramBot.commands.Command;
import bot.telegramBot.service.CommandExecService;
import bot.telegramBot.utils.BotResponse;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommandExecServiceImpl implements CommandExecService {

    private List<Command> commandList;

    @Override
    public SendMessage execCommand(Update update) {
        Long id = update.message().chat().id();
        try {
            String text = update.message().text();
            String command = text.split(" ")[0];

            for (var cmd : commandList) {
                if (!command.equalsIgnoreCase(cmd.command())) {
                    continue;
                }
                String response = cmd.handle(update);

                return new SendMessage(id, response);
            }

            return new SendMessage(id, BotResponse.NOT_SUPPORTED_COMMAND.msg);

        } catch (Exception ex) {
            return new SendMessage(id, BotResponse.SMTH_GONE_WRONG.msg + ": " +  ex.getMessage());
        }
    }
}
