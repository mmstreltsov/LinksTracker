package ru.tinkoff.utils.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.tinkoff.dto.LinkParserServiceResponse;
import ru.tinkoff.utils.LinkParser;

import java.util.Optional;


class GithubLinkParserTest {

    private LinkParser LinkParser;

    @BeforeEach
    void init() {
        LinkParser = new GithubLinkParser();
    }

    @Test
    void checkHostNameInResponseIsValid() {
        String link = "https://github.com/mmstreltsov/ahahahaha";

        Optional<LinkParserServiceResponse> response = LinkParser.getInfo(link);
        Assertions.assertTrue(response.isPresent());

        String actual = response.get().service().hostName;
        String excepted = "github.com";
        Assertions.assertEquals(excepted, actual);
    }

    @Test
    void getValidResponse() {
        String link = "https://github.com/mmstreltsov/tinkoff_java_backend";

        Optional<LinkParserServiceResponse> response = LinkParser.getInfo(link);
        Assertions.assertTrue(response.isPresent());

        String actual = response.get().value();
        String excepted = "mmstreltsov/tinkoff_java_backend";
        Assertions.assertEquals(excepted, actual);
    }

    @Test
    void getNullWhenInvalidHostname() {
        String link = "https://AAAAAAAAA.com/mmstreltsov/tinkoff_java_backend";

        Optional<LinkParserServiceResponse> response = LinkParser.getInfo(link);

        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    void getNullWhenThereIsNoRepository() {
        String link = "https://github.com/mmstreltsov";

        Assertions.assertThrows(IllegalStateException.class, () -> LinkParser.getInfo(link));
    }
}