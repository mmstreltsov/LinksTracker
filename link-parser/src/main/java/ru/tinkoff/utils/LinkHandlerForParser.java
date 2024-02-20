package ru.tinkoff.utils;

import ru.tinkoff.dto.LinkParserServiceResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public abstract class LinkHandlerForParser implements LinkParser {

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
        String serviceName = getServiceNameFromChild();

        if (!serviceName.equalsIgnoreCase(hostname)) {
            return Optional.empty();
        }

        String value = getValueFromChild(hostname, pathname);

        LinkParserServiceResponse response = LinkParserServiceResponse.builder()
                .service(serviceName)
                .value(value)
                .build();
        return Optional.of(response);
    }

    protected abstract String getValueFromChild(String hostname, String pathname);

    protected abstract String getServiceNameFromChild();
}
