package bot.telegramBot.commands.impl.utils;

import com.pengrad.telegrambot.model.Update;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
public class UriHandler {

    public static URI getLinkFromMessage(Update update) {
        String text = update.message().text();
        String[] words = text.split(" ");
        if (words.length < 2) {
            log.info("Can't get a link from text: " + text);
            throw new IllegalArgumentException("You should provide a link: /{command} {your_ulr}");
        }

        return parseStringToURI(words[1]);
    }

    public static URI parseStringToURI(String s) {
        try {
            return new URI(s);
        } catch (URISyntaxException e) {
            log.info("Can't parse link from text: " + s);
            throw new RuntimeException("Can't parse link: " + s);
        }
    }
}
