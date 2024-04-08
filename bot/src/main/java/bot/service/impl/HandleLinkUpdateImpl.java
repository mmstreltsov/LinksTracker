package bot.service.impl;

import bot.dto.LinkUpdateRequest;
import bot.service.HandleLinkUpdate;
import bot.telegramBot.bot.TelegramBotInit;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HandleLinkUpdateImpl implements HandleLinkUpdate {

    private final TelegramBotInit telegramBotInit;

    @Override
    public void handleLinkUpdate(LinkUpdateRequest request) {
        log.info("get request from MQ");

        request.tgChatIds.stream()
                .parallel()
                .forEach(chatId -> {
                    String text = request.url + ": " + request.description;

                    SendMessage sendMessage = new SendMessage(chatId, text);
                    telegramBotInit.getBot().execute(sendMessage);
                });

    }
}
