package scrapper.domain.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import scrapper.domain.LinkRepository;
import scrapper.domain.entity.Link;

@Repository
public class JpaLinkRepository implements LinkRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Link add(Link link) {
        entityManager.persist(link);
        entityManager.flush();
        entityManager.detach(link);
        return link;
    }

    @Override
    public Link update(Link link) {
        entityManager.merge(link);
        return link;
    }

    @Override
    public Link findById(Long id) {
        return entityManager.getReference(Link.class, id);
    }

    @Override
    public void remove(Link link) {
        if (!entityManager.contains(link)) {
            link = findById(link.getId());
        }
        entityManager.remove(link);
        entityManager.flush();
    }
}
