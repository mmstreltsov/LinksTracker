package scrapper.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.net.URI;
import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
public class LinkDTO {
    private Long id;
    private URI url;
    private OffsetDateTime updatedAt;
    private OffsetDateTime checkedAt;
}
