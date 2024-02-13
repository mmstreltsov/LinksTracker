package ru.tinkoff.utils.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.tinkoff.utils.LinkParser;


class GithubLinkParserTest {

    private LinkParser LinkParser;
    @BeforeEach
    void init() {
        LinkParser = new GithubLinkParser();
    }

    @Test
    void getValidResponse() {
        String link = "https://github.com/mmstreltsov/tinkoff_java_backend";

        String actual = LinkParser.getInfo(link);
        String excepted = "mmstreltsov:tinkoff_java_backend";
        Assertions.assertEquals(excepted, actual);
    }

    @Test
    void getNullWhenInvalidHostname() {
        String link = "https://AAAAAAAAA.com/mmstreltsov/tinkoff_java_backend";

        String actual = LinkParser.getInfo(link);
        String excepted = null;
        Assertions.assertEquals(excepted, actual);
    }

    @Test
    void getNullWhenThereIsNoRepository() {
        String link = "https://github.com/mmstreltsov";

        String actual = LinkParser.getInfo(link);
        String excepted = null;
        Assertions.assertEquals(excepted, actual);
    }
}