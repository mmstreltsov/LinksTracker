package ru.tinkoff.service.impl;

import org.springframework.stereotype.Service;
import ru.tinkoff.dto.LinkParserServiceResponse;
import ru.tinkoff.service.LinkParserService;
import ru.tinkoff.utils.LinkParser;

import java.util.List;
import java.util.Optional;

@Service
public class LinkParserServiceImpl implements LinkParserService {

    private final List<LinkParser> parsers;

    public LinkParserServiceImpl(List<LinkParser> parsers) {
        this.parsers = parsers;
    }

    @Override
    public Optional<LinkParserServiceResponse> getResponseFromAnyHost(String link) {
        for (LinkParser linkParser : parsers) {
            try {
                Optional<LinkParserServiceResponse> info = linkParser.getInfo(link);
                if (info.isPresent()) {
                    return info;
                }
            } catch (Throwable ignored) {
            }
        }
        return Optional.empty();
    }
}
