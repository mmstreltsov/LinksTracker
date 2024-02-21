package bot.client;

import bot.client.dto.LinkResponse;
import bot.client.dto.ListLinksResponse;

import java.net.URI;

public interface ScrapperClient {
    void registerAccount(Long id);
    void removeAccount(Long id);
    ListLinksResponse getTrackedLinks(Long id);
    LinkResponse addLink(Long id, URI link);
    LinkResponse removeLink(Long id, URI link);
}
