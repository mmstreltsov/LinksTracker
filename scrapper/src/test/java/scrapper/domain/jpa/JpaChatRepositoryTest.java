package scrapper.domain.jpa;

import dataBaseTests.IntegrationEnvironment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scrapper.domain.entity.Chat;
import scrapper.domain.entity.Link;

import java.time.OffsetDateTime;
import java.util.List;

@SpringBootTest
public class JpaChatRepositoryTest extends IntegrationEnvironment {

    @Autowired
    private JpaChatRepository jpaChatRepository;

    @Autowired
    private JpaLinkRepository jpaLinkRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    @Rollback
    void add_testCorrectLogic() {
        Chat chat = new Chat();
        Long chatId = 123L;
        chat.setChatId(chatId);

        Chat added = jpaChatRepository.add(chat);
        Assertions.assertAll(
                () -> Assertions.assertNotNull(added.getId()),
                () -> Assertions.assertEquals(chatId, added.getChatId())
        );
    }

    @Test
    @Transactional
    @Rollback
    void add_testDetachObject() {
        Chat chat = new Chat();
        Long chatId = 123L;
        chat.setChatId(chatId);

        Chat added = jpaChatRepository.add(chat);
        added.setChatId(-1L);

        Chat actual = jpaChatRepository.findById(added.getId());

        Assertions.assertAll(
                () -> Assertions.assertEquals(chatId, actual.getChatId())
        );
    }

    @Test
    @Transactional
    @Rollback
    void add_testCorrectIdValue() {
        Chat chat1 = new Chat();
        chat1.setChatId(123L);
        Chat added1 = jpaChatRepository.add(chat1);

        Chat chat2 = new Chat();
        chat2.setChatId(11L);
        Chat added2 = jpaChatRepository.add(chat2);
        Assertions.assertAll(
                () -> Assertions.assertEquals(added2.getId(), added1.getId() + 1)
        );
    }

    @Test
    @Transactional
    @Rollback
    void findById_testCorrectLogic() {
        Chat chat = new Chat();
        chat.setChatId(123L);
        Chat excepted = jpaChatRepository.add(chat);

        Assertions.assertNotNull(excepted.getId());
        Chat actual = jpaChatRepository.findById(excepted.getId());

        Assertions.assertNotNull(actual);
        Assertions.assertAll(
                () -> Assertions.assertEquals(excepted.getId(), actual.getId()),
                () -> Assertions.assertEquals(excepted.getChatId(), actual.getChatId())
        );
    }

    @Test
    @Transactional
    @Rollback
    void findById_testLinksAreCorrect() {
        Chat chat = new Chat();
        chat.setChatId(123L);
        Chat excepted = jpaChatRepository.add(chat);

        int size = 50;
        for (int i = 0; i < size; ++i) {
            Link link = new Link();
            link.setChat(excepted);
            link.setUrl("ahahahhahaaaa");

            jpaLinkRepository.add(link);
        }

        Chat actual = jpaChatRepository.findById(excepted.getId());
        System.out.println(actual);

        Assertions.assertNotNull(actual.getLinks());
        Assertions.assertEquals(size, actual.getLinks().size());
    }

    @Test
    @Transactional
    @Rollback
    void findById_NoResults() {
        Chat chat = new Chat();
        chat.setChatId(123L);
        jpaChatRepository.add(chat);

        Chat actual = jpaChatRepository.findByChatId(-1L);
        Assertions.assertNull(actual);
    }

    @Test
    @Transactional
    @Rollback
    void findByChatId_testCorrectLogic() {
        Chat chat = new Chat();
        chat.setChatId(123L);
        Chat excepted = jpaChatRepository.add(chat);

        Assertions.assertNotNull(excepted.getId());
        Chat actual = jpaChatRepository.findByChatId(excepted.getChatId());

        Assertions.assertNotNull(actual);
        Assertions.assertAll(
                () -> Assertions.assertEquals(excepted.getId(), actual.getId()),
                () -> Assertions.assertEquals(excepted.getChatId(), actual.getChatId())
        );
    }

    @Test
    @Transactional
    @Rollback
    void findByChatId_NoResults() {
        Chat chat = new Chat();
        chat.setChatId(123L);
        jpaChatRepository.add(chat);

        Chat actual = jpaChatRepository.findByChatId(-1L);
        Assertions.assertNull(actual);
    }

    @Test
    @Transactional
    @Rollback
    void findByChatId_testLinksAreCorrect() {
        Chat chat = new Chat();
        chat.setChatId(123L);
        Chat excepted = jpaChatRepository.add(chat);

        int size = 50;
        for (int i = 0; i < size; ++i) {
            Link link = new Link();
            link.setChat(excepted);
            link.setUrl("ahahahhahaaaa");

            jpaLinkRepository.add(link);
        }

        Chat actual = jpaChatRepository.findByChatId(excepted.getChatId());
        System.out.println(actual);

        Assertions.assertNotNull(actual.getLinks());
        Assertions.assertEquals(size, actual.getLinks().size());
    }

    @Test
    @Transactional
    @Rollback
    void update_testCorrectIdValue() {
        Chat chat = new Chat();
        chat.setChatId(23L);
        Chat added = jpaChatRepository.add(chat);

        Long newChatId = -1L;
        added.setChatId(newChatId);
        jpaChatRepository.update(added);

        Chat actual = jpaChatRepository.findById(added.getId());
        Assertions.assertAll(
                () -> Assertions.assertEquals(newChatId, actual.getChatId())
        );
    }

    @Test
    @Transactional
    @Rollback
    void remove_testCorrectLogic() {
        Chat chat = new Chat();
        chat.setChatId(123L);
        Chat excepted = jpaChatRepository.add(chat);


        Assertions.assertNotNull(jpaChatRepository.findById(excepted.getId()));
        jpaChatRepository.remove(excepted);

        Assertions.assertAll(
                () -> Assertions.assertThrows(EntityNotFoundException.class, () -> jpaChatRepository.findById(excepted.getId()).setId(1L))
        );
    }


    @Test
    @Transactional
    @Rollback
    void findAllChatWhatLinkUrlIs_testCorrectLogic() {
        int size = 5;
        String url = "findAllChatWhatLinkUrlIs";

        for (int i = 0; i < size; ++i) {
            Chat chat = new Chat();
            chat.setChatId(111L + i);
            Chat added = jpaChatRepository.add(chat);

            Link link = new Link();
            link.setChat(added);
            link.setUrl(url);
            link.setUpdatedAt(OffsetDateTime.now());
            jpaLinkRepository.add(link);
        }

        Pageable pageable = PageRequest.of(0, size);

        List<Chat> chats = jpaChatRepository.findAllChatWhatLinkUrlIs(url, pageable).getContent();
        Assertions.assertEquals(size, chats.size());
    }

    @Test
    @Transactional
    @Rollback
    void findAllChatWhatLinkUrlIs_testPagination() {
        int size = 54;
        String url = "findAllChatWhatLinkUrlIs";

        for (int i = 0; i < size; ++i) {
            Chat chat = new Chat();
            chat.setChatId(111L + i);
            Chat added = jpaChatRepository.add(chat);

            Link link = new Link();
            link.setChat(added);
            link.setUrl(url);
            link.setUpdatedAt(OffsetDateTime.now());
            jpaLinkRepository.add(link);
        }

        int page = 3;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Chat> chats = jpaChatRepository.findAllChatWhatLinkUrlIs(url, pageable);
        Assertions.assertAll(
                () -> Assertions.assertEquals(pageSize, chats.getContent().size()),
                () -> Assertions.assertEquals(page, chats.getNumber()),
                () -> Assertions.assertEquals(Math.ceil((double) size / pageSize), chats.getTotalPages())
        );
    }
}
