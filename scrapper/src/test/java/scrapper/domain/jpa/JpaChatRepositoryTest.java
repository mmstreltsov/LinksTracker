package scrapper.domain.jpa;

import dataBaseTests.IntegrationEnvironment;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scrapper.domain.entity.Chat;

@SpringBootTest
public class JpaChatRepositoryTest extends IntegrationEnvironment {

    @Autowired
    private JpaChatRepository jpaChatRepository;

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
}
