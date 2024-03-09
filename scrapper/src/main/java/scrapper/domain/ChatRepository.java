package scrapper.domain;

import scrapper.model.entity.Chat;
import scrapper.model.entity.Link;

import java.util.List;

public interface ChatRepository {
    Long addChatAndGetID(Chat chat);

    void removeChat(Chat chat);

    List<Chat> findAll();

    List<Chat> findAllByChatId(Long chatId);

    List<Link> findAllLinksByChatId(Long chatId);
}
