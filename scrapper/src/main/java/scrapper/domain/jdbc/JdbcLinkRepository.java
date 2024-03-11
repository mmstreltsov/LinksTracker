package scrapper.domain.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import scrapper.domain.LinkRepository;
import scrapper.model.entity.Link;

import java.sql.PreparedStatement;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Repository
public class JdbcLinkRepository implements LinkRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Link> rowMapper = new DataClassRowMapper<>(Link.class);

    @Override
    @Transactional
    public Long addLinkAndGetID(Link link) {
        final String insertIntoSql = "INSERT INTO links (url, updated_at, checked_at) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        if (link.getUpdatedAt() == null) {
            link.setUpdatedAt(OffsetDateTime.now());
        }

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(insertIntoSql, new String[]{"id"});
                    preparedStatement.setString(1, link.getUrl().toString());
                    preparedStatement.setObject(2, link.getUpdatedAt());
                    preparedStatement.setObject(3, link.getCheckedAt());
                    return preparedStatement;
                }, keyHolder
        );

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    @Transactional
    public void removeLink(Link link) {
        jdbcTemplate.update(
                "DELETE FROM links WHERE id=(?)", link.getId()
        );
    }

    @Override
    public List<Link> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM links",
                rowMapper
        );
    }

    @Override
    public Link findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM links WHERE id=(?)",
                rowMapper,
                id
        );
    }

    @Override
    public void updateCheckField(Link link) {
        jdbcTemplate.update(
                "UPDATE links SET checked_at = (?) WHERE id=(?)", OffsetDateTime.now(), link.getId()
        );
    }

    @Override
    public void updateUpdateField(Link link) {
        jdbcTemplate.update(
                "UPDATE links SET updated_at = (?) WHERE id=(?)", OffsetDateTime.now(), link.getId()
        );
    }

    @Override
    public List<Link> findLinksWithCheckedFieldLessThenGiven(OffsetDateTime time) {
        return jdbcTemplate.query(
                "SELECT * FROM links WHERE checked_at < (?) OR checked_at is null",
                rowMapper,
                time
        );
    }
}
