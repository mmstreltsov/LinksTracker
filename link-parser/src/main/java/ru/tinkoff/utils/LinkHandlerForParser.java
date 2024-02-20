package ru.tinkoff.utils;

import ru.tinkoff.dto.LinkParserServiceResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public abstract class LinkHandlerForParser implements LinkParser {

    protected ServiceNames serviceName;

    @Override
    public Optional<LinkParserServiceResponse> getInfo(String link) {
        URL url;
        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            return Optional.empty();
        }

        return getInfoFromHostAndPath(url.getHost(), url.getPath());
    }

    private Optional<LinkParserServiceResponse> getInfoFromHostAndPath(String hostname, String pathname) {
        setServiceNameFromChild();

        if (!serviceName.hostName.equalsIgnoreCase(hostname)) {
            return Optional.empty();
        }

        String value = getValueFromChild(hostname, pathname);

        LinkParserServiceResponse response = LinkParserServiceResponse.builder()
                .service(serviceName.hostName)
                .value(value)
                .build();
        return Optional.of(response);
    }

    protected abstract String getValueFromChild(String hostname, String pathname);

    protected abstract void setServiceNameFromChild();
}
