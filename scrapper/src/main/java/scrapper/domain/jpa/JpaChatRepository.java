package scrapper.domain.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import scrapper.domain.ChatRepository;
import scrapper.model.entity.Chat;

import java.util.List;

@Repository
public class JpaChatRepository implements ChatRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Chat addChat(Chat chat) {
        return null;
    }

    @Override
    public void removeChatById(Long id) {

    }

    @Override
    public void removeChatByChatId(Long chatId) {

    }

    @Override
    public List<Chat> findAll() {
        return entityManager.createQuery("SELECT c FROM Chat c", Chat.class).getResultList();
    }

    @Override
    public List<Chat> findAllByCurrentLinkUrl(String url) {
        return null;
    }
}
