package scrapper.model;

import scrapper.model.entity.Link;

import java.time.OffsetDateTime;
import java.util.List;

public interface LinkStorageService {
    Link addLink(Link link);

    void removeLink(Link link);

    List<Link> findAll();
    Link findLinkById(Long id);
    void setCheckFieldToNow(Link link);
    void setUpdateFieldToNow(Link link);
    List<Link> findLinksWithCheckedFieldLessThenGiven(OffsetDateTime time);
}
