package ru.tinkoff.dto;

import lombok.Builder;
import ru.tinkoff.utils.ServiceNames;

@Builder
public record LinkParserServiceResponse(ServiceNames service, String value) {
}
