package scrapper.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.net.URI;
import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
public class Link {
    private Long id;
    private URI url;
    private OffsetDateTime updatedAt;
    private OffsetDateTime checkedAt;
}
