package ru.tinkoff.service;

import ru.tinkoff.dto.LinkParserServiceResponse;

import java.util.Optional;

public interface LinkParserService {
    Optional<LinkParserServiceResponse> getResponseFromAnyHost(String link);
}
