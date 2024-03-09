package scrapper.model.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import scrapper.domain.LinkRepository;
import scrapper.model.LinkService;
import scrapper.model.entity.Link;

@Service
public class LinkServiceImpl implements LinkService {

    private final LinkRepository linkRepository;

    public LinkServiceImpl(@Qualifier("jdbcLinkRepository") LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public Link addLink(Link link) {
        Long id = linkRepository.addLinkAndGetID(link);
        return Link.builder()
                .id(id)
                .url(link.getUrl())
                .build();
    }

    @Override
    public void removeLink(Link link) {
        linkRepository.removeLink(link);
    }


    @Override
    public Link findLinkById(Long id) {
        return linkRepository.findById(id);
    }
}