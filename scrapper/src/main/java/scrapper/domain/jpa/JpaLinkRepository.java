package scrapper.domain.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import scrapper.domain.LinkRepository;
import scrapper.domain.entity.Link;

import java.time.OffsetDateTime;
import java.time.temporal.TemporalUnit;
import java.util.List;

@Repository
@Transactional
public class JpaLinkRepository implements LinkRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Link add(Link link) {
        if (link.getUpdatedAt() == null) {
            link.setUpdatedAt(OffsetDateTime.now());
        }
        if (link.getCheckedAt() == null) {
            link.setCheckedAt(OffsetDateTime.now());
        }

        entityManager.persist(link);
        entityManager.flush();
        return link;
    }

    @Override
    public Link update(Link link) {
        entityManager.merge(link);
        return link;
    }

    @Override
    public Link findById(Long id) {
        try {
            return entityManager.getReference(Link.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Link findByUlrAndChatId(String ulr, Long chatId) {
        try {
            return entityManager.createQuery("select l FROM Link l WHERE l.url = ?1 and l.chat.chatId = ?2", Link.class)
                    .setParameter(1, ulr)
                    .setParameter(2, chatId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Link> findAll() {
        return entityManager.createQuery("select l from Link l", Link.class)
                .getResultList();
    }

    @Override
    public Page<Link> findAllByUrl(String url, Pageable pageable) {
        List<Link> list = entityManager.createQuery("SELECT l FROM Link l WHERE l.url = ?1", Link.class)
                .setParameter(1, url)
                .setFirstResult(pageable.getPageSize() * pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        Long size = entityManager.createQuery("SELECT count(l) FROM Link l WHERE l.url = ?1", Long.class)
                .setParameter(1, url)
                .getSingleResult();
        return new PageImpl<>(list, pageable, size);
    }


    @Override
    public Page<Link> findUniqueUrlWhatNotCheckedForALongTime(int amount, TemporalUnit temporalUnit, Pageable pageable) {
        OffsetDateTime time = OffsetDateTime.now().minus(amount, temporalUnit);

        List<Link> links = entityManager.createQuery(
                        "SELECT l FROM Link l where l.id in (" +
                                "SELECT min(l.id) FROM Link l WHERE l.checkedAt is null or l.checkedAt < ?1 group by l.url" +
                                ")", Link.class)
                .setParameter(1, time)
                .setFirstResult(pageable.getPageSize() * pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();


        Long size = entityManager.createQuery("SELECT count(distinct l.url) FROM Link l WHERE l.checkedAt < ?1", Long.class)
                .setParameter(1, time)
                .getSingleResult();

        return new PageImpl<>(links, pageable, size);
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
