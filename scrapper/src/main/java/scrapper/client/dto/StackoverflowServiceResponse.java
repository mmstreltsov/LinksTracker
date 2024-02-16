package scrapper.client.dto;

import lombok.Data;

@Data
public class StackoverflowServiceResponse {
    public StackoverflowUnitServiceResponse[] items;
}
