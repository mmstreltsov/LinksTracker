package scrapper.model.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import scrapper.domain.LinkRepository;
import scrapper.model.LinkStorageService;
import scrapper.model.dto.LinkDTO;
import scrapper.model.dto.MapperEntityToDTO;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class LinkStorageServiceImpl implements LinkStorageService {

    private final LinkRepository linkRepository;
    private final MapperEntityToDTO mapper;

    public LinkStorageServiceImpl(@Qualifier("jdbcLinkRepository") LinkRepository linkRepository,
                                  MapperEntityToDTO mapper) {
        this.linkRepository = linkRepository;
        this.mapper = mapper;
    }

    @Override
    public LinkDTO addLink(LinkDTO linkDTO) {
        Long id = linkRepository.addLinkAndGetID(mapper.getLink(linkDTO));
        return LinkDTO.builder()
                .id(id)
                .url(linkDTO.getUrl())
                .build();
    }

    @Override
    public void removeLink(LinkDTO linkDTO) {
        linkRepository.removeLink(mapper.getLink(linkDTO));
    }

    @Override
    public List<LinkDTO> findAll() {
        return mapper.getLinkDtoList(linkRepository.findAll());
    }


    @Override
    public LinkDTO findLinkById(Long id) {
        return mapper.getLinkDto(linkRepository.findById(id));
    }

    @Override
    public void setCheckFieldToNow(LinkDTO linkDTO) {
        linkRepository.updateCheckField(mapper.getLink(linkDTO));
    }

    @Override
    public void setUpdateFieldToNow(LinkDTO linkDTO) {
        linkRepository.updateUpdateField(mapper.getLink(linkDTO));
    }

    @Override
    public List<LinkDTO> findLinksCheckedFieldLessThenGivenAndUniqueUrl(OffsetDateTime time) {
        return mapper.getLinkDtoList(linkRepository.findLinksCheckedFieldLessThenGivenAndUniqueUrl(time));
    }
}