package scrapper.domain;

import scrapper.model.entity.Link;

import java.util.List;

public interface LinkRepository {
    Long addLinkAndGetID(Link link);

    void removeLink(Link link);

    List<Link> findAll();
}
