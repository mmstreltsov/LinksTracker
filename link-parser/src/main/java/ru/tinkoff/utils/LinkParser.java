package ru.tinkoff.utils;

import ru.tinkoff.dto.LinkParserServiceResponse;

import java.util.Optional;

public interface LinkParser {
    Optional<LinkParserServiceResponse> getInfo(String url);
}