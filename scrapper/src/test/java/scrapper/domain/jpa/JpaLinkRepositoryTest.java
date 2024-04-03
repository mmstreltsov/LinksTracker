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
import scrapper.domain.entity.Link;

import java.time.OffsetDateTime;

@SpringBootTest
public class JpaLinkRepositoryTest extends IntegrationEnvironment {


    @Autowired
    private JpaChatRepository jpaChatRepository;

    @Autowired
    private JpaLinkRepository jpaLinkRepository;


    private Chat pushRandomChat() {
        Chat chat = new Chat();
        chat.setChatId(getChatIdUnique());
        return jpaChatRepository.add(chat);
    }

    private static Long chatIdUnique = 1111L;
    private static Long getChatIdUnique() {
        return chatIdUnique++;
    }

    @Test
    @Transactional
    @Rollback
    void add_testCorrectLogic() {
        Chat chat = pushRandomChat();
        String url = "ahaha";
        OffsetDateTime time = OffsetDateTime.now();

        Link link = new Link();
        link.setChat(chat);
        link.setUrl(url);
        link.setUpdatedAt(time);

        Link added = jpaLinkRepository.add(link);
        Assertions.assertAll(
                () -> Assertions.assertNotNull(added.getId()),
                () -> Assertions.assertEquals(url, added.getUrl()),
                () -> Assertions.assertEquals(time.getSecond(), added.getUpdatedAt().getSecond()),
                () -> Assertions.assertEquals(chat, added.getChat())
        );
    }

    @Test
    @Transactional
    @Rollback
    void add_testDetachObject() {
        String url = "ahaha";

        Link link = new Link();
        link.setChat(pushRandomChat());
        link.setUrl(url);
        link.setUpdatedAt(OffsetDateTime.now());


        Link added = jpaLinkRepository.add(link);
        added.setUrl("NONONO");

        Link actual = jpaLinkRepository.findById(added.getId());
        Assertions.assertEquals(url, actual.getUrl());
    }

    @Test
    @Transactional
    @Rollback
    void add_testCorrectIdValue() {
        Link link1 = new Link();
        link1.setChat(pushRandomChat());
        link1.setUrl("ahaha");
        link1.setUpdatedAt(OffsetDateTime.now());

        Link added1 = jpaLinkRepository.add(link1);

        Link link2 = new Link();
        link2.setChat(pushRandomChat());
        link2.setUrl("ahaha");
        link2.setUpdatedAt(OffsetDateTime.now());

        Link added2 = jpaLinkRepository.add(link2);

        Assertions.assertAll(
                () -> Assertions.assertEquals(added2.getId(), added1.getId() + 1)
        );
    }

    @Test
    @Transactional
    @Rollback
    void findById_testCorrectLogic() {
        Link link = new Link();
        link.setChat(pushRandomChat());
        link.setUrl("ahaha");
        link.setUpdatedAt(OffsetDateTime.now());
        Link excepted = jpaLinkRepository.add(link);

        Assertions.assertNotNull(excepted.getId());
        Link actual = jpaLinkRepository.findById(excepted.getId());

        Assertions.assertNotNull(actual);
        Assertions.assertAll(
                () -> Assertions.assertEquals(excepted.getId(), actual.getId()),
                () -> Assertions.assertEquals(excepted.getUpdatedAt().getSecond(), actual.getUpdatedAt().getSecond()),
                () -> Assertions.assertEquals(excepted.getCheckedAt(), actual.getCheckedAt()),
                () -> Assertions.assertEquals(excepted.getChat(), actual.getChat()),
                () -> Assertions.assertEquals(excepted.getUrl(), actual.getUrl())
        );
    }

    @Test
    @Transactional
    @Rollback
    void update_testCorrectIdValue() {
        Link link = new Link();
        link.setChat(pushRandomChat());
        link.setUrl("ahaha");
        link.setUpdatedAt(OffsetDateTime.now());
        Link added = jpaLinkRepository.add(link);

        String newUrl = "NONONO";
        added.setUrl(newUrl);
        jpaLinkRepository.update(added);

        Link actual = jpaLinkRepository.findById(added.getId());
        Assertions.assertAll(
                () -> Assertions.assertEquals(newUrl, actual.getUrl()),
                () -> Assertions.assertEquals(added.getUrl(), actual.getUrl())
        );
    }

    @Test
    @Transactional
    @Rollback
    void remove_testCorrectLogic() {
        Link link = new Link();
        link.setChat(pushRandomChat());
        link.setUrl("ahaha");
        link.setUpdatedAt(OffsetDateTime.now());
        Link excepted = jpaLinkRepository.add(link);


        Assertions.assertNotNull(jpaLinkRepository.findById(excepted.getId()));
        jpaLinkRepository.remove(excepted);

        Assertions.assertAll(
                () -> Assertions.assertThrows(EntityNotFoundException.class, () -> jpaLinkRepository.findById(excepted.getId()).setId(1L))
        );
    }
}
