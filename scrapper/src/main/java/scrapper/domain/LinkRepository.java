package scrapper.domain;

import scrapper.model.entity.Link;

import java.time.OffsetDateTime;
import java.util.List;

public interface LinkRepository {
    Long addLinkAndGetID(Link link);

    void removeLink(Link link);

    List<Link> findAll();

    Link findById(Long id);

    void updateCheckField(Link link);
    void updateUpdateField(Link link);

    List<Link> findLinksWithCheckedFieldLessThenGiven(OffsetDateTime time);
}
