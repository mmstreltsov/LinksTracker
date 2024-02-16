package scrapper.client;

import scrapper.client.dto.StackoverflowUnitServiceResponse;

public interface StackoverflowClient {
    StackoverflowUnitServiceResponse getInfo(String id);
}
