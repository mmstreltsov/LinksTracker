package ru.tinkoff.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.tinkoff.dto.LinkParserServiceResponse;
import ru.tinkoff.service.LinkParserService;
import ru.tinkoff.utils.LinkParser;
import ru.tinkoff.utils.impl.GithubLinkParser;
import ru.tinkoff.utils.impl.StackoverflowLinkParser;

import java.util.List;
import java.util.Optional;

class LinkParserServiceImplTest {

    private LinkParserService linkParserService;

    @BeforeEach
    void init() {
        List<LinkParser> linkParsers = List.of(new GithubLinkParser(), new StackoverflowLinkParser());
        linkParserService = new LinkParserServiceImpl(linkParsers);
    }

    @Test
    void getValidGithubResponse() {
        String url = "https://github.com/mmstreltsov/tinkoff_java_backend";
        Optional<LinkParserServiceResponse> response = linkParserService.getResponseFromAnyHost(url);
        Assertions.assertTrue(response.isPresent());
    }

    @Test
    void getValidStackoverflowResponse() {
        String url = "https://stackoverflow.com/questions/51789880/how-to-test-a-component-bean-in-spring-boot";
        Optional<LinkParserServiceResponse> response = linkParserService.getResponseFromAnyHost(url);
        Assertions.assertTrue(response.isPresent());
    }

    @Test
    void getInvalidResponse() {
        String url = "aaaaaaaaaaa";
        Optional<LinkParserServiceResponse> response = linkParserService.getResponseFromAnyHost(url);
        Assertions.assertTrue(response.isEmpty());
    }
}