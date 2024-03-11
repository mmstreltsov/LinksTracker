package ru.tinkoff.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.service.LinkParserService;
import ru.tinkoff.service.impl.LinkParserServiceImpl;
import ru.tinkoff.utils.LinkParser;
import ru.tinkoff.utils.impl.GithubLinkParser;
import ru.tinkoff.utils.impl.StackoverflowLinkParser;

import java.util.List;

@Configuration
public class LinkParserConfiguration {

    @Bean
    public LinkParser githubLinkParser() {
        return new GithubLinkParser();
    }

    @Bean
    public LinkParser stackoverflowLinkParser() {
        return new StackoverflowLinkParser();
    }

    @Bean
    public LinkParserService linkParserService(List<LinkParser> linkParsers) {
        return new LinkParserServiceImpl(linkParsers);
    }
}
