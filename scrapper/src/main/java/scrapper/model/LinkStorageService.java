package scrapper.model;

import scrapper.model.dto.LinkDTO;

import java.time.OffsetDateTime;
import java.util.List;

public interface LinkStorageService {
    LinkDTO addLink(LinkDTO linkDTO);

    void removeLink(LinkDTO linkDTO);

    List<LinkDTO> findAll();
    LinkDTO findLinkById(Long id);
    void setCheckFieldToNow(LinkDTO linkDTO);
    void setUpdateFieldToNow(LinkDTO linkDTO);
    List<LinkDTO> findLinksCheckedFieldLessThenGivenAndUniqueUrl(OffsetDateTime time);
}
