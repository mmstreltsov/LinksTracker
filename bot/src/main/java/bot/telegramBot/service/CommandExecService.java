package bot.telegramBot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface CommandExecService {
    SendMessage execCommand(Update update);
}
