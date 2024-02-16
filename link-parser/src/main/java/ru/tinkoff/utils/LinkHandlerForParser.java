package ru.tinkoff.utils;

import ru.tinkoff.dto.LinkParserServiceResponse;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class LinkHandlerForParser implements LinkParser {
    @Override
    public LinkParserServiceResponse getInfo(String link) {
        URL url;
        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            return null;
        }

        return getInfoFromHostAndPath(url.getHost(), url.getPath());
    }

    public abstract LinkParserServiceResponse getInfoFromHostAndPath(String hostname, String pathname);
}
