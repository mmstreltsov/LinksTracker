package ru.tinkoff.utils.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.tinkoff.utils.LinkParser;

class StackoverflowLinkParserTest {
    private LinkParser LinkParser;

    @BeforeEach
    void init() {
        LinkParser = new StackoverflowLinkParser();
    }

    @Test
    void getValidResponse() {
        String link = "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c";

        String actual = LinkParser.getInfo(link);
        String excepted = "1642028";
        Assertions.assertEquals(excepted, actual);
    }

    @Test
    void getNullWhenInvalidHostname() {
        String link = "https://AAAAAAAAA.com/questions/1642028/what-is-the-operator-in-c";

        String actual = LinkParser.getInfo(link);
        String excepted = null;
        Assertions.assertEquals(excepted, actual);
    }

    @Test
    void getNullWhenThereIsNoId() {
        String link = "https://stackoverflow.com/search?q=unsupported%20link";

        String actual = LinkParser.getInfo(link);
        String excepted = null;
        Assertions.assertEquals(excepted, actual);
    }
}