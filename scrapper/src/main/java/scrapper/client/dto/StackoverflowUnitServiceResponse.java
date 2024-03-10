package scrapper.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StackoverflowUnitServiceResponse {
    public OffsetDateTime last_activity_date;
    public String title;
}
