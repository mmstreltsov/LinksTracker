package scrapper.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import scrapper.domain.entity.Chat;

public interface ChatRepository {
    Chat add(Chat chat);
    Chat update(Chat chat);

    Chat findById(Long id);
    Chat findByChatId(Long chatId);
    void remove(Chat chat);

    Page<Chat> findAllChatWhatLinkUrlIs(String url, Pageable pageable);
}
