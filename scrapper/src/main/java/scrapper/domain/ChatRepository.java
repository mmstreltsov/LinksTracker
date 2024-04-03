package scrapper.domain;

import scrapper.model.entity.Chat;

import java.util.List;

public interface ChatRepository {
    Chat addChat(Chat chat);

    void removeChatById(Long id);

    void removeChatByChatId(Long chatId);

    List<Chat> findAll();

    List<Chat> findAllByCurrentLinkUrl(String url);
}
