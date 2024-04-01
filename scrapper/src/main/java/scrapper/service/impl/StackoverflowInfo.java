package scrapper.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.tinkoff.dto.LinkParserServiceResponse;
import ru.tinkoff.utils.ServiceNames;
import scrapper.client.StackoverflowClient;
import scrapper.client.dto.StackoverflowUnitServiceResponse;
import scrapper.controllers.errors.ClientException;
import scrapper.service.GetInfoFromApiService;
import scrapper.service.dto.LastUpdatedDTO;

@Service
@AllArgsConstructor
public class StackoverflowInfo implements GetInfoFromApiService {

    private final StackoverflowClient stackoverflowClient;
    private final ServiceNames serviceName = ServiceNames.STACKOVERFLOW;

    @Override
    public LastUpdatedDTO getInfo(LinkParserServiceResponse meta) {
        if (!meta.service().equals(serviceName)) {
            throw new IllegalArgumentException("Not that service");
        }

        StackoverflowUnitServiceResponse infoFromQuestion;
        try {
            infoFromQuestion = stackoverflowClient.getInfoFromQuestion(meta.value());
        } catch (Throwable ex) {
            throw new IllegalStateException("Request went wrong: " + ex.getMessage());
        }

        return LastUpdatedDTO.builder()
                .metaInfo(infoFromQuestion.title)
                .updatedAt(infoFromQuestion.last_activity_date)
                .build();
    }
}