package scrapper.domain.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import scrapper.domain.LinkRepository;
import scrapper.model.entity.Link;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public class JpaLinkRepository implements LinkRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Link addLink(Link link) {
        return null;
    }

    @Override
    public void removeLink(Link link) {

    }

    @Override
    public List<Link> findAll() {
        return entityManager.createQuery("SELECT l FROM Link l", Link.class).getResultList();
    }

    @Override
    public Link findById(Long id) {
        return null;
    }

    @Override
    public List<Link> findByChatId(Long chatId) {
        return null;
    }

    @Override
    public List<Link> findAllByUrl(String url) {
        return null;
    }

    @Override
    public void updateCheckField(Link link) {

    }

    @Override
    public void updateUpdateField(Link link) {

    }

    @Override
    public List<Link> findLinksCheckedFieldLessThenGivenAndUniqueUrl(OffsetDateTime time) {
        return null;
    }
}
