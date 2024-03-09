package scrapper.model;

import scrapper.model.entity.Link;

public interface LinkService {
    Link addLink(Link link);

    void removeLink(Link link);

    Link findLinkById(Long id);
}
