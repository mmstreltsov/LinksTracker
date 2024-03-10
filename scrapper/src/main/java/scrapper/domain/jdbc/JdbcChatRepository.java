package scrapper.domain.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import scrapper.domain.ChatRepository;
import scrapper.model.entity.Chat;
import scrapper.model.entity.Link;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Repository
public class JdbcChatRepository implements ChatRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Chat> rowMapper = new DataClassRowMapper<>(Chat.class);

    @Override
    @Transactional
    public Long addChatAndGetID(Chat chat) {
        final String insertIntoSql = "INSERT INTO chat (chat_id, link_id) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(insertIntoSql, new String[]{"id"});
                    preparedStatement.setInt(1, chat.getChatId().intValue());
                    preparedStatement.setInt(2, chat.getLinkId().intValue());
                    return preparedStatement;
                }, keyHolder
        );

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    @Transactional
    public void removeChat(Chat chat) {
        jdbcTemplate.update(
                "DELETE FROM chat WHERE chat_id=(?);", chat.getChatId()
        );
    }

    @Override
    public List<Chat> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM chat;",
                rowMapper
        );
    }

    @Override
    public List<Chat> findAllByChatId(Long chatId) {
        return jdbcTemplate.query(
                "SELECT * FROM chat WHERE chat_id=(?);",
                rowMapper,
                chatId);
    }

    @Override
    @Transactional
    public List<Chat> findAllByCurrentLinkUrl(String url) {
        String sqlQuery =
                "WITH l AS (SELECT * FROM links WHERE url=(?)) " +
                "SELECT * " +
                "FROM chat c " +
                "INNER JOIN l " +
                "ON c.link_id = l.id;";
        return jdbcTemplate.query(
                sqlQuery,
                new DataClassRowMapper<>(Chat.class),
                url);
    }

    @Override
    public Chat findChatByChatIdAndLinkId(Long chatId, Long linkId) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM chat WHERE chat_id=(?) AND link_id=(?);",
                rowMapper,
                chatId, linkId);
    }

    /**
     Return distinct links with stubs in 'id', 'updated' and 'checked' columns
     */
    @Override
    @Transactional
    public List<Link> findAllLinksByChatId(Long chatId) {
        String sqlQuery =
                "WITH t AS (SELECT * FROM chat WHERE chat_id=(?)) " +
                "SELECT DISTINCT -1 as id, l.url as url, null as updated_at, null as checked_at " +
                "FROM links l " +
                "INNER JOIN t " +
                "ON t.link_id = l.id;";
        return jdbcTemplate.query(
                sqlQuery,
                new DataClassRowMapper<>(Link.class),
                chatId);
    }
}
