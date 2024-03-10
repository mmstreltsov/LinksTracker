package scrapper.service;

import ru.tinkoff.dto.LinkParserServiceResponse;
import scrapper.service.dto.LastUpdatedDTO;

public interface GetInfoFromApiService {
    LastUpdatedDTO getInfo(LinkParserServiceResponse meta);
}
