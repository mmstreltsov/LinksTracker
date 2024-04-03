package scrapper.domain.jdbc;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import scrapper.domain.ChatRepository;
import scrapper.domain.entity.Chat;

import java.sql.PreparedStatement;
import java.util.List;

//@Repository
@AllArgsConstructor
public class JdbcChatRepository  {
//    private final JdbcTemplate jdbcTemplate;
//    private final RowMapper<Chat> rowMapper = new DataClassRowMapper<>(Chat.class);
//
//    @Override
//    @Transactional
//    public Chat addChat(Chat chat) {
//        final String insertIntoSql = "INSERT INTO chat (chat_id) VALUES (?)";
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        jdbcTemplate.update(
//                connection -> {
//                    PreparedStatement preparedStatement = connection.prepareStatement(insertIntoSql, new String[]{"id"});
//                    preparedStatement.setInt(1, chat.getChatId().intValue());
//                    return preparedStatement;
//                }, keyHolder
//        );
//
//        long id = keyHolder.getKey().longValue();
//        return new Chat(id, chat.getChatId(), chat.getLinks());
//    }
//
//    @Override
//    public void removeChatById(Long id) {
//        jdbcTemplate.update(
//                "DELETE FROM chat WHERE id=(?);", id
//        );
//    }
//
//    @Override
//    @Transactional
//    public void removeChatByChatId(Long chatId) {
//        jdbcTemplate.update(
//                "DELETE FROM chat WHERE chat_id=(?);", chatId
//        );
//    }
//
//    @Override
//    public List<Chat> findAll() {
//        return jdbcTemplate.query(
//                "SELECT * FROM chat c " +
//                        "LEFT JOIN links l " +
//                        "ON c.chat_id = l.chat_id;",
//                rowMapper
//        );
//    }
//
//    @Override
//    @Transactional
//    public List<Chat> findAllByCurrentLinkUrl(String url) {
//        String sqlQuery =
//                "WITH l AS (SELECT * FROM links WHERE url=(?)) " +
//                        "SELECT * " +
//                        "FROM chat c " +
//                        "INNER JOIN l " +
//                        "ON c.link_id = l.id;";
//        return jdbcTemplate.query(
//                sqlQuery,
//                new DataClassRowMapper<>(Chat.class),
//                url);
//    }
}