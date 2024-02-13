package ru.tinkoff.service.impl;

import org.springframework.stereotype.Service;
import ru.tinkoff.service.LinkParserService;
import ru.tinkoff.utils.LinkParser;

import java.util.List;
import java.util.Objects;

@Service
public class LinkParserServiceImpl implements LinkParserService {

    private final List<LinkParser> parsers;

    public LinkParserServiceImpl(List<LinkParser> parsers) {
        this.parsers = parsers;
    }

    @Override
    public String getResponseFromAnyHost(String link) {
        for (LinkParser linkParser : parsers) {
            try {
                String info = linkParser.getInfo(link);
                if (Objects.nonNull(info)) {
                    return info;
                }
            } catch (Exception ignored) {}
        }
        return null;
    }
}
