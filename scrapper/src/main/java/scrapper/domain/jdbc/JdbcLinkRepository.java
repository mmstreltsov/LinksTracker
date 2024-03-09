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
        final String insertIntoSql = "INSERT INTO links (url) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(insertIntoSql, new String[]{"id"});
                    preparedStatement.setString(1, link.getUrl().toString());
                    return preparedStatement;
                }, keyHolder
        );

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    @Transactional
    public void removeLink(Link link) {
        jdbcTemplate.update(
                "DELETE FROM links WHERE id=(?)", link.getId());
    }

    @Override
    @Transactional
    public List<Link> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM links",
                rowMapper
        );
    }

    @Override
    @Transactional
    public Link findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM links WHERE id=(?)",
                rowMapper,
                id
        );
    }
}
