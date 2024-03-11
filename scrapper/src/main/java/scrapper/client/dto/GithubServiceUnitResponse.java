package scrapper.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GithubServiceUnitResponse {
    public String type;
    public String id;
    public OffsetDateTime created_at;
}
