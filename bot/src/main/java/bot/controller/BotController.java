package bot.controller;


import bot.dto.ApiErrorResponse;
import bot.dto.LinkUpdateRequest;
import bot.telegramBot.bot.TelegramBotInit;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BotController {

    private final TelegramBotInit telegramBotInit;

    @PostMapping(value = "/updates")
    public ResponseEntity<Object> updates(@RequestBody LinkUpdateRequest linkUpdateRequest) {
        try {
            linkUpdateRequest.tgChatIds.stream()
                    .parallel()
                    .forEach(chatId -> {
                        String text = linkUpdateRequest.url + ": " + linkUpdateRequest.description;

                        SendMessage sendMessage = new SendMessage(chatId, text);
                        telegramBotInit.getBot().execute(sendMessage);
                    });
        } catch (Throwable t) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrorResponse());
        }
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
