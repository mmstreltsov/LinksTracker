package scrapper.service;

import scrapper.service.dto.LastUpdatedDTO;

public interface GetResponseFromAnyHost {

    LastUpdatedDTO getResponse(String url);
}
