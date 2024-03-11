package scrapper.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Builder
@Data
@AllArgsConstructor
public class LastUpdatedDTO {
    private String metaInfo;
    private OffsetDateTime updatedAt;
}
