package ru.tinkoff.utils.impl;

import org.springframework.stereotype.Component;
import ru.tinkoff.utils.LinkHandlerForParser;
import ru.tinkoff.utils.LinkParser;
import ru.tinkoff.utils.ServiceNames;

@Component
public class StackoverflowLinkParser extends LinkHandlerForParser implements LinkParser {

    @Override
    protected String getValueFromChild(String hostname, String pathname) {
        String[] pathNodes = pathname.split("/");
        if (pathNodes.length < 3) {
            throw new IllegalStateException("Can't parse the link");
        }

        return pathNodes[2];
    }

    @Override
    protected void setServiceNameFromChild() {
        this.serviceName = ServiceNames.STACKOVERFLOW;
    }
}
