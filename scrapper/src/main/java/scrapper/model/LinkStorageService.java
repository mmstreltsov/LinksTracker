package scrapper.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import scrapper.model.dto.LinkDTO;

import java.time.OffsetDateTime;
import java.time.temporal.TemporalUnit;

public interface LinkStorageService {
    void addLink(LinkDTO linkDTO);

    void removeLink(LinkDTO linkDTO);

    void setCheckFieldToNow(LinkDTO linkDTO);
    void setUpdateFieldToValue(LinkDTO linkDTO, OffsetDateTime time);
    Page<LinkDTO> findUniqueUrlWhatNotCheckedForALongTime(int amount, TemporalUnit temporalUnit, Pageable pageable);
}
