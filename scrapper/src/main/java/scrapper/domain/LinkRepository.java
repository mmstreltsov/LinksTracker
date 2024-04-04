package scrapper.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import scrapper.domain.entity.Chat;
import scrapper.domain.entity.Link;

import java.time.temporal.TemporalUnit;

public interface LinkRepository {
    Link add(Link link);
    Link update(Link link);

    Link findById(Long id);
    Link findByUlrAndChatId(String ulr, Long chatId);

    Page<Link> findAllByUrl(String url, Pageable pageable);
    Page<Link> findUniqueUrlWhatNotCheckedForALongTime(int amount, TemporalUnit temporalUnit, Pageable pageable);

    void remove(Link link);
}
