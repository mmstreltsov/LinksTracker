package scrapper.domain;

import scrapper.model.entity.Chat;
import scrapper.model.entity.Link;

import java.util.List;

public interface ChatRepository {
    Long addChatAndGetID(Chat chat);

    void removeChat(Chat chat);

    List<Chat> findAll();

    List<Chat> findAllByChatId(Long chatId);

    List<Chat> findAllByCurrentLinkUrl(String url);

    Chat findChatByChatIdAndLinkId(Long chatId, Long linkId);

    List<Link> findAllLinksByChatId(Long chatId);
}
