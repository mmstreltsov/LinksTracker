package ru.tinkoff.dto;

import lombok.Builder;

@Builder
public record LinkParserServiceResponse(String service, String value) {
}
