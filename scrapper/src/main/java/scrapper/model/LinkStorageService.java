package scrapper.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import scrapper.model.dto.LinkDTO;

import java.time.temporal.TemporalUnit;

public interface LinkStorageService {
    void addLink(LinkDTO linkDTO);

    void removeLink(LinkDTO linkDTO);

    void setCheckFieldToNow(LinkDTO linkDTO);
    void setUpdateFieldToNow(LinkDTO linkDTO);
    Page<LinkDTO> findUniqueUrlWhatNotCheckedForALongTime(int amount, TemporalUnit temporalUnit, Pageable pageable);
}
