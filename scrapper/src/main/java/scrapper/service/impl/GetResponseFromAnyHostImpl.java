package scrapper.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.dto.LinkParserServiceResponse;
import ru.tinkoff.service.LinkParserService;
import scrapper.service.GetInfoFromApiService;
import scrapper.service.GetResponseFromAnyHost;
import scrapper.service.dto.LastUpdatedDTO;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class GetResponseFromAnyHostImpl implements GetResponseFromAnyHost {

    private List<GetInfoFromApiService> services;
    private final LinkParserService linkParserService;

    @Override
    public LastUpdatedDTO getResponse(String url) {
        Optional<LinkParserServiceResponse> optResponse = linkParserService.getResponseFromAnyHost(url);
        if (optResponse.isEmpty()) {
            throw new IllegalArgumentException("No matched link parser");
        }

        LinkParserServiceResponse response = optResponse.get();
        for (var service : services) {
            try {
                return service.getInfo(response);
            } catch (Throwable ignored) {
            }
        }

        throw new RuntimeException("Can not get info from API");
    }
}
