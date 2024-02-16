package ru.tinkoff.utils;

import ru.tinkoff.dto.LinkParserServiceResponse;

public interface LinkParser {
    LinkParserServiceResponse getInfo(String url);
}