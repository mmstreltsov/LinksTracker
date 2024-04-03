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
import scrapper.model.entity.Link;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

//@SpringBootTest
class JdbcLinkRepositoryTest {} /*extends IntegrationEnvironment {
    @Autowired
    private JdbcLinkRepository jdbcLinkRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final RowMapper<Link> rowMapper = new DataClassRowMapper<>(Link.class);

    @Test
    void findAll_testCorrectLogic() {
        List<Link> excepted = jdbcTemplate.query("SELECT * FROM links", rowMapper);
        List<Link> actual = jdbcLinkRepository.findAll();

        Assertions.assertTrue(excepted.containsAll(actual) && actual.containsAll(excepted), "Returned values are not equal");
    }

    @Test
    @Transactional
    @Rollback
    void addLinkWithLinkAndGetID_testInsertElem() {
        List<Link> prev = jdbcLinkRepository.findAll();

        Link link = Link.builder()
                .url(URI.create("ahaha"))
                .updatedAt(OffsetDateTime.now())
                .build();

        Link addLink = jdbcLinkRepository.addLink(link);

        List<Link> post = jdbcLinkRepository.findAll();
        Assertions.assertAll(
                () -> Assertions.assertTrue(post.stream().map(Link::getId).toList().contains(addLink.getId())),
                () -> Assertions.assertEquals(post.size(), prev.size() + 1)
        );
    }

    @Test
    @Transactional
    @Rollback
    void addLinkWithLinkAndGetID_testValidIdSetter() {
        Link link = Link.builder()
                .url(URI.create("ahaha"))
                .updatedAt(OffsetDateTime.now())
                .build();

        Link link1 = jdbcLinkRepository.addLink(link);
        Link link2 = jdbcLinkRepository.addLink(link);
        Assertions.assertEquals(link2.getId(), link1.getId() + 1);
    }

    @Test
    @Transactional
    @Rollback
    void removeLink_testCorrectLogic() {
        Link link = Link.builder()
                .url(URI.create("ahaha"))
                .updatedAt(OffsetDateTime.now())
                .build();

        Link addLink = jdbcLinkRepository.addLink(link);

        List<Link> prev = jdbcLinkRepository.findAll();
        jdbcLinkRepository.removeLink(addLink);
        List<Link> post = jdbcLinkRepository.findAll();

        Assertions.assertAll(
                () -> Assertions.assertFalse(post.contains(addLink)),
                () -> Assertions.assertEquals(post.size(), prev.size() - 1)
        );
    }

    @Test
    @Transactional
    @Rollback
    void findById_testCorrectLogic() {
        Link link = Link.builder()
                .url(URI.create("ahaha"))
                .updatedAt(OffsetDateTime.now())
                .build();

        Link addLink = jdbcLinkRepository.addLink(link);
        Link actual = jdbcLinkRepository.findById(addLink.getId());

        Assertions.assertAll(
                () -> Assertions.assertEquals(addLink.getUrl(), actual.getUrl()),
                () -> Assertions.assertEquals(addLink.getId(), actual.getId())
        );
    }

    @Test
    @Transactional
    @Rollback
    void findAllByUrl_testCorrectLogic() {
        Link link = Link.builder()
                .url(URI.create("ahaha"))
                .updatedAt(OffsetDateTime.now())
                .build();

        int count = 5;

        for (int i = 0; i < count; ++i) {
            jdbcLinkRepository.addLink(link);
        }

        List<Link> actual = jdbcLinkRepository.findAllByUrl(link.getUrl().toString());

        Assertions.assertEquals(actual.size(), count);
    }

    @Test
    @Transactional
    @Rollback
    void setCheckFieldToNow_testCorrectLogic() {
        OffsetDateTime start = OffsetDateTime.now();

        Link link = Link.builder()
                .url(URI.create("ahaha"))
                .updatedAt(OffsetDateTime.now())
                .build();

        link = jdbcLinkRepository.addLink(link);

        jdbcLinkRepository.updateCheckField(link);

        OffsetDateTime checkedAt = jdbcLinkRepository.findById(link.getId()).getCheckedAt();

        OffsetDateTime end = OffsetDateTime.now();
        Assertions.assertAll(
                () -> Assertions.assertTrue(start.isBefore(checkedAt)),
                () -> Assertions.assertTrue(end.isAfter(checkedAt))
        );
    }

    @Test
    @Transactional
    @Rollback
    void setCheckFieldToNow_testCorrectLogicWithMultipleUrls() {
        OffsetDateTime start = OffsetDateTime.now();

        Link link = Link.builder()
                .url(URI.create("ahaha"))
                .updatedAt(OffsetDateTime.now())
                .build();

        jdbcLinkRepository.addLink(link);
        jdbcLinkRepository.addLink(link);


        jdbcLinkRepository.updateCheckField(link);


        List<Link> linkS = jdbcLinkRepository.findAllByUrl(link.getUrl().toString());
        OffsetDateTime end = OffsetDateTime.now();

        linkS.forEach(it -> {
            Assertions.assertAll(
                    () -> Assertions.assertTrue(start.isBefore(it.getCheckedAt())),
                    () -> Assertions.assertTrue(end.isAfter(it.getCheckedAt()))
            );
        });
    }

    @Test
    @Transactional
    @Rollback
    void setUpdateFieldToNow_testCorrectLogic() {
        OffsetDateTime start = OffsetDateTime.now();

        Link link = Link.builder()
                .url(URI.create("ahaha"))
                .updatedAt(OffsetDateTime.now())
                .build();

        link = jdbcLinkRepository.addLink(link);
        OffsetDateTime middle = OffsetDateTime.now();

        jdbcLinkRepository.updateUpdateField(link);

        OffsetDateTime updatedAt = jdbcLinkRepository.findById(link.getId()).getUpdatedAt();

        OffsetDateTime end = OffsetDateTime.now();
        Assertions.assertAll(
                () -> Assertions.assertTrue(start.isBefore(updatedAt)),
                () -> Assertions.assertTrue(middle.isBefore(updatedAt)),
                () -> Assertions.assertTrue(end.isAfter(updatedAt))
        );
    }

    @Test
    @Transactional
    @Rollback
    void setUpdateFieldToNow_testCorrectLogicWithMultipleUrls() {
        OffsetDateTime start = OffsetDateTime.now();

        Link link = Link.builder()
                .url(URI.create("ahaha"))
                .updatedAt(OffsetDateTime.now())
                .build();

        jdbcLinkRepository.addLink(link);
        jdbcLinkRepository.addLink(link);


        jdbcLinkRepository.updateUpdateField(link);


        List<Link> linkS = jdbcLinkRepository.findAllByUrl(link.getUrl().toString());
        OffsetDateTime end = OffsetDateTime.now();

        linkS.forEach(it -> {
            Assertions.assertAll(
                    () -> Assertions.assertTrue(start.isBefore(it.getUpdatedAt())),
                    () -> Assertions.assertTrue(end.isAfter(it.getUpdatedAt()))
            );
        });
    }

    @Test
    @Transactional
    @Rollback
    void findLinksWith_CheckedFieldLessThen_UniqueUrl__testCorrectLogic() {
        List<Long> ids = new ArrayList<>();

        OffsetDateTime now = OffsetDateTime.now();

        for (int i = 0; i < 10; ++i) {
            Link link = Link.builder()
                    .url(URI.create(String.valueOf(i)))
                    .updatedAt(now)
                    .checkedAt(now.plusMinutes(i))
                    .build();

            link = jdbcLinkRepository.addLink(link);
            ids.add(link.getId());
        }

        List<Link> linkS = jdbcLinkRepository.findLinksCheckedFieldLessThenGivenAndUniqueUrl(OffsetDateTime.now().plusMinutes(5).plusSeconds(15));

        List<Long> excepted = ids.subList(0, 6);
        List<Long> actual = linkS.stream().map(Link::getId).toList();

        Assertions.assertTrue(actual.containsAll(excepted));
    }

    @Test
    @Transactional
    @Rollback
    void findLinksWith_CheckedFieldLessThen_UniqueUrl__testUniqueValues() {
        for (int i = 0; i < 10; ++i) {
            String v = String.valueOf(i % 7);

            Link link = Link.builder()
                    .url(URI.create(v))
                    .updatedAt(OffsetDateTime.now())
                    .checkedAt(OffsetDateTime.MIN)
                    .build();

            jdbcLinkRepository.addLink(link);
        }

        List<Link> linkS = jdbcLinkRepository.findLinksCheckedFieldLessThenGivenAndUniqueUrl(OffsetDateTime.now());

        List<String> actual = linkS.stream().map(it -> it.getUrl().toString()).toList();
        List<String> uniqueActual = actual.stream().distinct().toList();

        Assertions.assertEquals(actual.size(), uniqueActual.size());
    }

    @Test
    @Transactional
    @Rollback
    void getLinkWithCheckedFieldLessThenGiven_testCorrectLogicCaseNull() {
        Link link = Link.builder()
                .url(URI.create("stub"))
                .build();

        link = jdbcLinkRepository.addLink(link);

        List<Link> linkS = jdbcLinkRepository.findLinksCheckedFieldLessThenGivenAndUniqueUrl(OffsetDateTime.now().plusMinutes(5).plusSeconds(15));
        List<Long> actual = linkS.stream().map(Link::getId).toList();

        Assertions.assertTrue(actual.contains(link.getId()));
    }
}
*/