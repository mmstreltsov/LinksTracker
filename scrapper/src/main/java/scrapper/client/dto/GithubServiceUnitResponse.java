package scrapper.client.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class GithubServiceUnitResponse {
    public String type;
    public String id;
    public OffsetDateTime created_at;
}
