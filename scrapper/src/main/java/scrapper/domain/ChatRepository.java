package scrapper.domain;

import scrapper.model.entity.Chat;

import java.util.List;

public interface ChatRepository {
    Long addChatAndGetID(Chat chat);

    void removeChat(Chat chat);

    List<Chat> findAll();
}
