package scrapper.domain.jpa;

import dataBaseTests.IntegrationEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JpaChatRepositoryTest extends IntegrationEnvironment {

    @Autowired
    private JpaChatRepository jpaChatRepository;

//    @Autowired
//    private JpaLinkRepository jpaLinkRepository;

    @Test
    @Transactional
    @Rollback
    void test() {
        System.out.println(jpaChatRepository.findAll());
        System.out.println(jpaChatRepository.findAll().get(0).getLinks());
    }
}
