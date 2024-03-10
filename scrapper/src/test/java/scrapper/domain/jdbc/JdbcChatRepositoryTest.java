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
import scrapper.model.entity.Link;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class JdbcChatRepositoryTest extends IntegrationEnvironment {

    @Autowired
    private JdbcChatRepository jdbcChatRepository;
    @Autowired
    private JdbcLinkRepository jdbcLinkRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final RowMapper<Chat> rowMapper = new DataClassRowMapper<>(Chat.class);

    void initializeLinkObject() {
        Link link = Link.builder()
                .id(-1L)
                .url(URI.create("nononon"))
                .updatedAt(OffsetDateTime.now())
                .build();

        jdbcLinkRepository.addLinkAndGetID(link);
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

    @Test
    @Transactional
    @Rollback
    void findAllByChatId_testCorrectLogic() {
        initializeLinkObject();

        Chat chat = Chat.builder()
                .chatId(-1L)
                .linkId(1L)
                .build();

        int count = 5;
        for (int i = 0; i < count; i++) {
            jdbcChatRepository.addChatAndGetID(chat);
        }

        List<Chat> post = jdbcChatRepository.findAllByChatId(chat.getChatId());

        Assertions.assertEquals(post.size(), count);
    }

    @Test
    @Transactional
    @Rollback
    void findAllLinksByChatId_testCorrectLogic() {
        List<String> urls = List.of("one", "two", "three");
        Long chatId = -5L;

        urls.stream().parallel()
                .map(URI::create)
                .forEach(it -> {
                    Long id = jdbcLinkRepository.addLinkAndGetID(Link.builder().url(it).build());

                    Chat chat = Chat.builder()
                            .chatId(chatId)
                            .linkId(id)
                            .build();
                    jdbcChatRepository.addChatAndGetID(chat);
                });


        List<Link> post = jdbcChatRepository.findAllLinksByChatId(chatId);
        Assertions.assertTrue(post.stream().map(it -> it.getUrl().toString()).allMatch(urls::contains));
    }

    @Test
    @Transactional
    @Rollback
    void findChatByChatIdAndLinkId_testCorrectLogic() {
        initializeLinkObject();

        Chat chat = Chat.builder()
                .chatId(-5L)
                .linkId(1L)
                .build();
        jdbcChatRepository.addChatAndGetID(chat);


        Chat actual = jdbcChatRepository.findChatByChatIdAndLinkId(chat.getChatId(), chat.getLinkId());
        Assertions.assertAll(
                () -> Assertions.assertEquals(chat.getChatId(), actual.getChatId()),
                () -> Assertions.assertEquals(chat.getLinkId(), actual.getLinkId())
        );
    }
    @Test
    @Transactional
    @Rollback
    void findAllByCurrentLinkUrl_testCorrectLogic() {
        String url = "ahaha_ohohoho";
        Long id = jdbcLinkRepository.addLinkAndGetID(Link.builder().url(URI.create(url)).build());

        List<Chat> chatList = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            Chat chat = Chat.builder()
                    .chatId(-1L)
                    .linkId(id)
                    .build();

            Long idChat = jdbcChatRepository.addChatAndGetID(chat);
            chat.setId(idChat);

            chatList.add(chat);
        }


        List<Chat> allChatsWithCurrentUrl = jdbcChatRepository.findAllByCurrentLinkUrl(url);
        Assertions.assertTrue(allChatsWithCurrentUrl.containsAll(chatList));
    }
}