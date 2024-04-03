package scrapper.domain;

import scrapper.domain.entity.Link;

import java.time.OffsetDateTime;
import java.util.List;

public interface LinkRepository {
    Link add(Link link);
    Link update(Link link);

    Link findById(Long id);
    void remove(Link link);
}
