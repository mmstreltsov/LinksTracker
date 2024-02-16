package ru.tinkoff.utils.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.tinkoff.dto.LinkParserServiceResponse;
import ru.tinkoff.utils.LinkParser;


class GithubLinkParserTest {

    private LinkParser LinkParser;
    @BeforeEach
    void init() {
        LinkParser = new GithubLinkParser();
    }

    @Test
    void checkHostNameInResponseIsValid() {
        String link = "https://github.com/mmstreltsov/ahahahaha";

        LinkParserServiceResponse response = LinkParser.getInfo(link);

        String actual = response.service;
        String excepted = "github.com";
        Assertions.assertEquals(excepted, actual);
    }
    @Test
    void getValidResponse() {
        String link = "https://github.com/mmstreltsov/tinkoff_java_backend";

        LinkParserServiceResponse response = LinkParser.getInfo(link);

        String actual = response.value;
        String excepted = "mmstreltsov/tinkoff_java_backend";
        Assertions.assertEquals(excepted, actual);
    }

    @Test
    void getNullWhenInvalidHostname() {
        String link = "https://AAAAAAAAA.com/mmstreltsov/tinkoff_java_backend";

        LinkParserServiceResponse response = LinkParser.getInfo(link);

        Assertions.assertNull(response);
    }

    @Test
    void getNullWhenThereIsNoRepository() {
        String link = "https://github.com/mmstreltsov";

        LinkParserServiceResponse response = LinkParser.getInfo(link);

        Assertions.assertNull(response);
    }
}