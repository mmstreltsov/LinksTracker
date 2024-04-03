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
import scrapper.domain.entity.Link;

import java.sql.PreparedStatement;
import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
//@Repository
public class JdbcLinkRepository {

//    private final JdbcTemplate jdbcTemplate;
//
//    private final RowMapper<Link> rowMapper = new DataClassRowMapper<>(Link.class);
//
//    @Override
//    @Transactional
//    public Link addLink(Link link) {
//        final String insertIntoSql = "INSERT INTO links (url, chat_id, updated_at, checked_at) VALUES (?, ?, ?, ?)";
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        if (link.getUpdatedAt() == null) {
//            link.setUpdatedAt(OffsetDateTime.now());
//        }
//
//        jdbcTemplate.update(
//                connection -> {
//                    PreparedStatement preparedStatement = connection.prepareStatement(insertIntoSql, new String[]{"id"});
//                    preparedStatement.setString(1, link.getUrl().toString());
//                    preparedStatement.setObject(2, link.getChat().getChatId());
//                    preparedStatement.setObject(3, link.getUpdatedAt());
//                    preparedStatement.setObject(4, link.getCheckedAt());
//                    return preparedStatement;
//                }, keyHolder
//        );
//
//        long id = keyHolder.getKey().longValue();
//        return Link.builder()
//                .id(id)
//                .url(link.getUrl())
//                .chat(link.getChat())
//                .checkedAt(link.getCheckedAt())
//                .updatedAt(link.getUpdatedAt())
//                .build();
//    }
//
//    @Override
//    public void removeLink(Link link) {
//        jdbcTemplate.update(
//                "DELETE FROM links WHERE id=(?)", link.getId()
//        );
//    }
//
//    @Override
//    public List<Link> findAll() {
//        return jdbcTemplate.query(
//                "SELECT * FROM links",
//                rowMapper
//        );
//    }
//
//    @Override
//    public Link findById(Long id) {
//        return jdbcTemplate.queryForObject(
//                "SELECT * FROM links WHERE id=(?)",
//                rowMapper,
//                id
//        );
//    }
//    @Override
//    public List<Link> findByChatId(Long chatId) {
//        return jdbcTemplate.query(
//                "SELECT * FROM links WHERE chat_id=(?)",
//                rowMapper,
//                chatId
//        );
//    }
//
//    @Override
//    public List<Link> findAllByUrl(String url) {
//        return jdbcTemplate.query(
//                "SELECT * FROM links WHERE url=(?)",
//                rowMapper,
//                url
//        );
//    }
//
//    @Override
//    public void updateCheckField(Link link) {
//        jdbcTemplate.update(
//                "UPDATE links SET checked_at = (?) WHERE url=(?)", OffsetDateTime.now(), link.getUrl().toString()
//        );
//    }
//
//    @Override
//    public void updateUpdateField(Link link) {
//        jdbcTemplate.update(
//                "UPDATE links SET updated_at = (?) WHERE url=(?)", OffsetDateTime.now(), link.getUrl().toString()
//        );
//    }
//
//    @Override
//    public List<Link> findLinksCheckedFieldLessThenGivenAndUniqueUrl(OffsetDateTime time) {
//        return jdbcTemplate.query(
//                "WITH t as (SELECT l.*, row_number() over (partition by l.url order by random()) as rown FROM links l) " +
//                        "SELECT t.id, t.url, t.updated_at, t.checked_at FROM t " +
//                        "WHERE (t.checked_at < (?) OR t.checked_at is null) " +
//                        "AND t.rown = 1 ",
//                rowMapper,
//                time
//        );
//    }
}
