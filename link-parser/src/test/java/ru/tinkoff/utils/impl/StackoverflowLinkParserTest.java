package ru.tinkoff.utils.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.tinkoff.dto.LinkParserServiceResponse;
import ru.tinkoff.utils.LinkParser;

import java.util.Optional;

class StackoverflowLinkParserTest {
    private LinkParser LinkParser;

    @BeforeEach
    void init() {
        LinkParser = new StackoverflowLinkParser();
    }


    @Test
    void checkHostNameInResponseIsValid() {
        String link = "https://stackoverflow.com/questions/7777777";

        Optional<LinkParserServiceResponse> response = LinkParser.getInfo(link);
        Assertions.assertTrue(response.isPresent());

        String actual = response.get().service().hostName;
        String excepted = "stackoverflow.com";
        Assertions.assertEquals(excepted, actual);
    }

    @Test
    void getValidResponse() {
        String link = "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c";

        Optional<LinkParserServiceResponse> response = LinkParser.getInfo(link);
        Assertions.assertTrue(response.isPresent());

        String actual = response.get().value();
        String excepted = "1642028";
        Assertions.assertEquals(excepted, actual);
    }

    @Test
    void getNullWhenInvalidHostname() {
        String link = "https://AAAAAAAAA.com/questions/1642028/what-is-the-operator-in-c";

        Optional<LinkParserServiceResponse> response = LinkParser.getInfo(link);

        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    void getNullWhenThereIsNoId() {
        String link = "https://stackoverflow.com/search?q=unsupported%20link";

        Assertions.assertThrows(IllegalStateException.class, () -> LinkParser.getInfo(link));
    }
}