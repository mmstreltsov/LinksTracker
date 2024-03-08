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
                "DELETE FROM chat WHERE chat_id=(?);", chat.getChatId());
    }

    @Override
    @Transactional
    public List<Chat> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM chat;",
                rowMapper
        );
    }
}
