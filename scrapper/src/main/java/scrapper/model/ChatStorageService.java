package scrapper.model;

import scrapper.model.entity.Chat;
import scrapper.model.entity.Link;

import java.util.List;

public interface ChatStorageService {
    Chat addUser(Chat chat);

    void removeUser(Long chatId);

    void removeByChatIdAndLinkId(Long chatId, Long linkId);

    List<Chat> findAllChatsByChatId(Long id);

    List<Chat> findAllChatsByCurrentUrl(String url);

    List<Link> findAllLinksByChatId(Long id);
}
