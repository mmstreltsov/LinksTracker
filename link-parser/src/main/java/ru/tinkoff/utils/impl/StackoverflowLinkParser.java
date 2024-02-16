package ru.tinkoff.utils.impl;

import org.springframework.stereotype.Component;
import ru.tinkoff.dto.LinkParserServiceResponse;
import ru.tinkoff.utils.LinkHandlerForParser;
import ru.tinkoff.utils.LinkParser;
import ru.tinkoff.utils.ServiceNames;

@Component
public class StackoverflowLinkParser extends LinkHandlerForParser implements LinkParser {

    private final String SERVICE_NAME = ServiceNames.STACKOVERFLOW.hostName;

    @Override
    public LinkParserServiceResponse getInfoFromHostAndPath(String hostname, String pathname) {
        if (!SERVICE_NAME.equalsIgnoreCase(hostname)) {
            return null;
        }

        String[] pathNodes = pathname.split("/");
        if (pathNodes.length < 3) {
            return null;
        }

        String value = pathNodes[2];

        return LinkParserServiceResponse.builder()
                .service(SERVICE_NAME)
                .value(value)
                .build();
    }
}
