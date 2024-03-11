package scrapper.model.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import scrapper.domain.LinkRepository;
import scrapper.model.LinkStorageService;
import scrapper.model.entity.Link;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class LinkStorageServiceImpl implements LinkStorageService {

    private final LinkRepository linkRepository;

    public LinkStorageServiceImpl(@Qualifier("jdbcLinkRepository") LinkRepository linkRepository) {
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
    public List<Link> findAll() {
        return linkRepository.findAll();
    }


    @Override
    public Link findLinkById(Long id) {
        return linkRepository.findById(id);
    }

    @Override
    public void setCheckFieldToNow(Link link) {
        linkRepository.updateCheckField(link);
    }

    @Override
    public void setUpdateFieldToNow(Link link) {
        linkRepository.updateUpdateField(link);
    }

    @Override
    public List<Link> findLinksWithCheckedFieldLessThenGiven(OffsetDateTime time) {
        return linkRepository.findLinksWithCheckedFieldLessThenGiven(time);
    }
}