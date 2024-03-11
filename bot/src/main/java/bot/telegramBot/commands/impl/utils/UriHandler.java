package bot.telegramBot.commands.impl.utils;

import com.pengrad.telegrambot.model.Update;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
public class UriHandler {

    public static URI getLinkFromMessage(Update update) {
        String text = update.message().text();
        String[] words = text.split(" ");
        if (words.length < 2) {
            log.info("Can't get a link from text: " + text);
            throw new IllegalArgumentException("You should provide a link: /{command} {your_ulr}");
        }

        return URI.create(words[1]);
    }
}
