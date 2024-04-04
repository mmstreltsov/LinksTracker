package scrapper.domain.jpa;

import dataBaseTests.IntegrationEnvironment;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scrapper.domain.entity.Chat;
import scrapper.domain.entity.Link;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

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
    void add_testUpdatedTimeDeclaration() {
        Chat chat = pushRandomChat();
        String url = "ahaha";

        Link link = new Link();
        link.setChat(chat);
        link.setUrl(url);

        Link added = jpaLinkRepository.add(link);
        Assertions.assertAll(
                () -> Assertions.assertTrue(added.getUpdatedAt().isBefore(OffsetDateTime.now()))
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
    void findByUlrAndChatId() {
        Chat chat = pushRandomChat();
        String url = "ahaha";

        Link link = new Link();
        link.setChat(chat);
        link.setUrl(url);
        link.setUpdatedAt(OffsetDateTime.now());
        Link excepted = jpaLinkRepository.add(link);

        Link actual = jpaLinkRepository.findByUlrAndChatId(url, chat.getChatId());
        Assertions.assertEquals(excepted, actual);
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

    @Test
    @Transactional
    @Rollback
    void findAllByUrl_testCorrectLogic() {
        int size = 100;
        for (int i = 0; i < size; ++i) {
            Link link = new Link();
            link.setChat(pushRandomChat());
            link.setUrl("findAllByUrl");
            link.setUpdatedAt(OffsetDateTime.now());
            jpaLinkRepository.add(link);
        }

        int page = 0;
        int pageSize = 1000;
        Page<Link> links = jpaLinkRepository.findAllByUrl("findAllByUrl", PageRequest.of(page, pageSize));

        Assertions.assertAll(
                () -> Assertions.assertEquals(size, links.getContent().size())
        );
    }

    @Test
    @Transactional
    @Rollback
    void findAllByUrl_testPagination() {
        int size = 100;
        for (int i = 0; i < size; ++i) {
            Link link = new Link();
            link.setChat(pushRandomChat());
            link.setUrl("findAllByUrl");
            link.setUpdatedAt(OffsetDateTime.now());
            jpaLinkRepository.add(link);
        }

        int page = 2;
        int pageSize = 10;
        Page<Link> links = jpaLinkRepository.findAllByUrl("findAllByUrl", PageRequest.of(page, pageSize));

        Assertions.assertAll(
                () -> Assertions.assertEquals(pageSize, links.getSize()),
                () -> Assertions.assertEquals(Math.ceil((double) size / pageSize), links.getTotalPages()),
                () -> Assertions.assertEquals(page, links.getNumber())
        );
    }


    @Test
    @Transactional
    @Rollback
    void findUniqueUrlWhatNotCheckedForALongTime_testCorrectLogic() {
        for (int i = 0; i < 100; ++i) {
            Link link = new Link();
            link.setChat(pushRandomChat());
            link.setUrl("findUniqueUrlWhatNotCheckedForALongTime" + i);
            link.setUpdatedAt(OffsetDateTime.now());
            link.setCheckedAt(OffsetDateTime.now().minusMinutes(i));
            jpaLinkRepository.add(link);
        }

        int page = 0;
        int pageSize = 200;
        Page<Link> urls = jpaLinkRepository.findUniqueUrlWhatNotCheckedForALongTime(60, ChronoUnit.MINUTES, PageRequest.of(page, pageSize));

        OffsetDateTime time = OffsetDateTime.now().minusMinutes(60);
        for (Link link : urls.getContent()) {
            Assertions.assertTrue(link.getCheckedAt() == null || time.isAfter(link.getCheckedAt()));
        }
    }

    @Test
    @Transactional
    @Rollback
    void findUniqueUrlWhatNotCheckedForALongTime_testUniqueValues() {
        for (int i = 0; i < 100; ++i) {
            Link link = new Link();
            link.setChat(pushRandomChat());
            link.setUrl("findUniqueUrlWhatNotCheckedForALongTime" + i % 15);
            link.setUpdatedAt(OffsetDateTime.now());
            link.setCheckedAt(OffsetDateTime.now().minusMinutes(i));
            jpaLinkRepository.add(link);
        }

        int page = 0;
        int pageSize = 200;
        Page<Link> urls = jpaLinkRepository.findUniqueUrlWhatNotCheckedForALongTime(60, ChronoUnit.MINUTES, PageRequest.of(page, pageSize));

        Set<String> uniqueUrls = new HashSet<>();
        for (Link link : urls.getContent()) {
            Assertions.assertTrue(uniqueUrls.add(link.getUrl()));
        }
    }
}
