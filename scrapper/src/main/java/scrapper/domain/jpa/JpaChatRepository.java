package scrapper.domain.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import scrapper.domain.ChatRepository;
import scrapper.domain.entity.Chat;

import java.util.List;

@Repository
public class JpaChatRepository implements ChatRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Chat add(Chat chat) {
        entityManager.persist(chat);
        entityManager.flush();
        entityManager.detach(chat);
        return chat;
    }

    @Override
    public Chat update(Chat chat) {
        entityManager.merge(chat);
        return chat;
    }

    @Override
    public Chat findById(Long id) {
        return entityManager.getReference(Chat.class, id);
    }

    @Override
    public Chat findByChatId(Long chatId) {
        return entityManager.createQuery("SELECT c FROM Chat c WHERE c.chatId = ?1", Chat.class)
                .setParameter(1, chatId)
                .getResultList()
                .get(0);
    }

    @Override
    public void remove(Chat chat) {
        if (!entityManager.contains(chat)) {
            chat = findById(chat.getId());
        }
        entityManager.remove(chat);
        entityManager.flush();
    }

    @Override
    public Page<Chat> findAllChatWhatLinkUrlIs(String url, Pageable pageable) {
        List<Chat> list = entityManager.createQuery("SELECT c FROM Chat c JOIN Link l WHERE l.url = ?1 and l.chat = c", Chat.class)
                .setParameter(1, url)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        Long size = entityManager.createQuery("SELECT count(c) FROM Chat c JOIN Link l WHERE l.url = ?1 and l.chat = c", Long.class)
                .setParameter(1, url)
                .getSingleResult();

        return new PageImpl<>(list, pageable, size);
    }
}
