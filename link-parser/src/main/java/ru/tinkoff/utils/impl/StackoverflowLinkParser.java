package ru.tinkoff.utils.impl;

import org.springframework.stereotype.Component;
import ru.tinkoff.utils.LinkHandlerForParser;
import ru.tinkoff.utils.LinkParser;

@Component
public class StackoverflowLinkParser extends LinkHandlerForParser implements LinkParser {

    @Override
    public String getInfoFromHostAndPath(String hostname, String pathname) {
        if (!"stackoverflow.com".equalsIgnoreCase(hostname)) {
            return null;
        }

        String[] pathNodes = pathname.split("/");
        if (pathNodes.length < 3) {
            return null;
        }

        return pathNodes[2];
    }
}
