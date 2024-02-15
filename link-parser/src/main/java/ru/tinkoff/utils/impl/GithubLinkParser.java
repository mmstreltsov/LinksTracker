package ru.tinkoff.utils.impl;

import org.springframework.stereotype.Component;
import ru.tinkoff.utils.LinkHandlerForParser;
import ru.tinkoff.utils.LinkParser;

@Component
public class GithubLinkParser extends LinkHandlerForParser implements LinkParser {

    @Override
    public String getInfoFromHostAndPath(String hostname, String pathname) {
        if (!"Github.com".equalsIgnoreCase(hostname)) {
            return null;
        }

        String[] pathNodes = pathname.split("/");
        if (pathNodes.length < 3) {
            return null;
        }

        String user = pathNodes[1], repository = pathNodes[2];
        return user + ":" + repository;
    }
}
