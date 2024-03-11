package scrapper.client;

import scrapper.client.dto.StackoverflowUnitServiceResponse;

public interface StackoverflowClient {
    StackoverflowUnitServiceResponse getInfoFromQuestion(String id);
}
