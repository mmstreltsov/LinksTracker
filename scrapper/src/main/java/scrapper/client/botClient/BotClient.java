package scrapper.client.botClient;

import scrapper.model.entity.Chat;
import scrapper.model.entity.Link;

import java.util.List;

public interface BotClient {
    void updateLink(List<Chat> chat, Link link);
}
