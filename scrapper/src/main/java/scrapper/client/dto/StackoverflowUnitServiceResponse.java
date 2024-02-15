package scrapper.client.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class StackoverflowUnitServiceResponse {
    public OffsetDateTime last_activity_date;
    public String title;
}
