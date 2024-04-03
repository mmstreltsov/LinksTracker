package scrapper.domain;

import scrapper.domain.entity.Chat;

import java.util.List;

public interface ChatRepository {
    Chat add(Chat chat);
    Chat update(Chat chat);

    Chat findById(Long id);
    Chat findByChatId(Long chatId);
    void remove(Chat chat);

    List<Chat> findAllChatWhatLinkUrlIs(String url);
}
