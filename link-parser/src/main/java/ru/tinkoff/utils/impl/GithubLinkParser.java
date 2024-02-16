package ru.tinkoff.utils.impl;

import org.springframework.stereotype.Component;
import ru.tinkoff.dto.LinkParserServiceResponse;
import ru.tinkoff.utils.LinkHandlerForParser;
import ru.tinkoff.utils.LinkParser;
import ru.tinkoff.utils.ServiceNames;

@Component
public class GithubLinkParser extends LinkHandlerForParser implements LinkParser {

    private final String SERVICE_NAME = ServiceNames.GITHUB.hostName;

    @Override
    public LinkParserServiceResponse getInfoFromHostAndPath(String hostname, String pathname) {
        if (!SERVICE_NAME.equalsIgnoreCase(hostname)) {
            return null;
        }

        String[] pathNodes = pathname.split("/");
        if (pathNodes.length < 3) {
            return null;
        }

        String user = pathNodes[1], repository = pathNodes[2];
        String value = user + "/" + repository;
        return LinkParserServiceResponse.builder()
                .service(SERVICE_NAME)
                .value(value)
                .build();
    }
}
