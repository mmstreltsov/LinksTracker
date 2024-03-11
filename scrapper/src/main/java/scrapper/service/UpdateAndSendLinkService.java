package scrapper.service;

import scrapper.model.entity.Link;

public interface UpdateAndSendLinkService {

    void handle(Link link);
}
