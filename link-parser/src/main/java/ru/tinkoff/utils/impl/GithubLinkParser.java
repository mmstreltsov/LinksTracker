package ru.tinkoff.utils.impl;

import ru.tinkoff.utils.LinkHandlerForParser;
import ru.tinkoff.utils.LinkParser;
import ru.tinkoff.utils.ServiceNames;

public class GithubLinkParser extends LinkHandlerForParser implements LinkParser {

    @Override
    protected String getValueFromChild(String hostname, String pathname) {
        String[] pathNodes = pathname.split("/");
        if (pathNodes.length < 3) {
            throw new IllegalStateException("Can't parse the link");
        }

        String user = pathNodes[1], repository = pathNodes[2];
        return user + "/" + repository;
    }

    @Override
    protected ServiceNames getServiceNameFromChild() {
        return ServiceNames.GITHUB;
    }
}
