package scrapper.domain.jdbc;

import lombok.SneakyThrows;
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
import java.util.List;

@SpringBootTest
class JdbcLinkRepositoryTest {
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
    @SneakyThrows
    @Transactional
    @Rollback
    void addLinkWithLinkAndGetID_testInsertElem() {
        List<Link> prev = jdbcLinkRepository.findAll();

        Link link = Link.builder()
                .url(URI.create("ahaha"))
                .build();

        Long id = jdbcLinkRepository.addLinkAndGetID(link);
        link.setId(id);

        List<Link> post = jdbcLinkRepository.findAll();
        Assertions.assertAll(
                () -> Assertions.assertTrue(post.contains(link)),
                () -> Assertions.assertEquals(post.size(), prev.size() + 1)
        );
    }

    @Test
    @SneakyThrows
    @Transactional
    @Rollback
    void addLinkWithLinkAndGetID_testValidIdSetter() {
        Link link = Link.builder()
                .url(URI.create("ahaha"))
                .build();

        Long firstId = jdbcLinkRepository.addLinkAndGetID(link);
        Long secondId = jdbcLinkRepository.addLinkAndGetID(link);
        Assertions.assertEquals(secondId, firstId + 1);
    }

    @Test
    @SneakyThrows
    @Transactional
    @Rollback
    void removeLink_testCorrectLogic() {
        Link link = Link.builder()
                .url(URI.create("ahaha"))
                .build();

        Long id = jdbcLinkRepository.addLinkAndGetID(link);
        link.setId(id);

        List<Link> prev = jdbcLinkRepository.findAll();
        jdbcLinkRepository.removeLink(link);
        List<Link> post = jdbcLinkRepository.findAll();

        Assertions.assertAll(
                () -> Assertions.assertFalse(post.contains(link)),
                () -> Assertions.assertEquals(post.size(), prev.size() - 1)
        );
    }

    @Test
    @SneakyThrows
    @Transactional
    @Rollback
    void findById_testCorrectLogic() {
        Link link = Link.builder()
                .url(URI.create("ahaha"))
                .build();

        Long id = jdbcLinkRepository.addLinkAndGetID(link);
        link.setId(id);

        Link actual = jdbcLinkRepository.findById(id);

        Assertions.assertAll(
                () -> Assertions.assertEquals(link.getUrl(), actual.getUrl()),
                () -> Assertions.assertEquals(link.getId(), actual.getId())
        );
    }
}