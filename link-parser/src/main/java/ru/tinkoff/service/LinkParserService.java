package ru.tinkoff.service;

import ru.tinkoff.dto.LinkParserServiceResponse;

public interface LinkParserService {
    LinkParserServiceResponse getResponseFromAnyHost(String link);
}
