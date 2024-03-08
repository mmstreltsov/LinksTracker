package scrapper.domain.jdbc;


import dataBaseTests.IntegrationEnvironment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scrapper.model.entity.Chat;

import java.util.List;

@SpringBootTest
class JdbcChatRepositoryTest extends IntegrationEnvironment {

    @Autowired
    private JdbcChatRepository jdbcChatRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final RowMapper<Chat> rowMapper = new DataClassRowMapper<>(Chat.class);

    void initializeLinkObject() {
        jdbcTemplate.update("INSERT INTO links (url) VALUES ('nononon')");
    }
    @Test
    void findAll_testCorrectLogic() {
        List<Chat> excepted = jdbcTemplate.query("SELECT * FROM chat", rowMapper);
        List<Chat> actual = jdbcChatRepository.findAll();

        Assertions.assertTrue(excepted.containsAll(actual) && actual.containsAll(excepted), "Returned values are not equal");
    }

    @Test
    @Transactional
    @Rollback
    void addChatWithLinkAndGetID_testInsertElem() {
        initializeLinkObject();
        List<Chat> prev = jdbcChatRepository.findAll();

        Chat chat = Chat.builder()
                .chatId(-1L)
                .linkId(1L)
                .build();

        Long id = jdbcChatRepository.addChatAndGetID(chat);
        chat.setId(id);

        List<Chat> post = jdbcChatRepository.findAll();
        Assertions.assertAll(
                () -> Assertions.assertTrue(post.contains(chat)),
                () -> Assertions.assertEquals(post.size(), prev.size() + 1)
        );
    }

    @Test
    @Transactional
    @Rollback
    void addChatWithLinkAndGetID_testValidIdSetter() {
        initializeLinkObject();

        Chat chat = Chat.builder()
                .chatId(-1L)
                .linkId(1L)
                .build();

        Long firstId = jdbcChatRepository.addChatAndGetID(chat);
        Long secondId = jdbcChatRepository.addChatAndGetID(chat);
        Assertions.assertEquals(secondId, firstId + 1);
    }

    @Test
    @Transactional
    @Rollback
    void removeChat_testCorrectLogic() {
        initializeLinkObject();
        Chat chat = Chat.builder()
                .chatId(-1L)
                .linkId(1L)
                .build();
        int count = 5;
        for (int i = 0; i < count; i++) {
            jdbcChatRepository.addChatAndGetID(chat);
        }

        List<Chat> prev = jdbcChatRepository.findAll();
        jdbcChatRepository.removeChat(chat);
        List<Chat> post = jdbcChatRepository.findAll();

        Assertions.assertEquals(prev.size() - count, post.size());
    }
}