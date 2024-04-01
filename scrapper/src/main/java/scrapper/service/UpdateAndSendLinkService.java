package scrapper.service;

import scrapper.model.dto.LinkDTO;

public interface UpdateAndSendLinkService {

    void handle(LinkDTO linkDTO);
}
