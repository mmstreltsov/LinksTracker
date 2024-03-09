package scrapper.model;

import scrapper.model.entity.Chat;
import scrapper.model.entity.Link;

import java.util.List;

public interface LinkStorageService {

    Chat addUser(Chat chat);

    Link addLink(Link link);

    void removeUser(Long chatId);

    void removeLink(Link link);

    List<Chat> findAllChatsByChatId(Long id);

    List<Link> findAllLinksByChatId(Long id);

    Link findLinkById(Long id);
}
