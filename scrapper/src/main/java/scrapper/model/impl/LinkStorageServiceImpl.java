package scrapper.model.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import scrapper.domain.LinkRepository;
import scrapper.model.LinkStorageService;
import scrapper.model.dto.LinkDTO;
import scrapper.model.dto.MapperEntityWithDTO;
import scrapper.domain.entity.Link;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class LinkStorageServiceImpl implements LinkStorageService {

    private final LinkRepository linkRepository;
    private final MapperEntityWithDTO mapper;

    public LinkStorageServiceImpl(LinkRepository linkRepository,
                                  MapperEntityWithDTO mapper) {
        this.linkRepository = linkRepository;
        this.mapper = mapper;
    }

    @Override
    public LinkDTO addLink(LinkDTO linkDTO) {
//        Link link = linkRepository.addLink(mapper.getLink(linkDTO));
//        return mapper.getLinkDto(link);
        return null;

    }

    @Override
    public void removeLink(LinkDTO linkDTO) {
//        linkRepository.removeLink(mapper.getLink(linkDTO));
    }

    @Override
    public void setCheckFieldToNow(LinkDTO linkDTO) {
//        linkRepository.updateCheckField(mapper.getLink(linkDTO));
    }

    @Override
    public void setUpdateFieldToNow(LinkDTO linkDTO) {
//        linkRepository.updateUpdateField(mapper.getLink(linkDTO));
    }

    @Override
    public List<LinkDTO> findLinksCheckedFieldLessThenGivenAndUniqueUrl(OffsetDateTime time) {
//        return mapper.getLinkDtoList(linkRepository.findLinksCheckedFieldLessThenGivenAndUniqueUrl(time));
        return null;
    }
}