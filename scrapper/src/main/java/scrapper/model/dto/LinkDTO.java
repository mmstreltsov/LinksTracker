package scrapper.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import scrapper.domain.entity.Chat;

import java.net.URI;
import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LinkDTO {
    private Long id;
    private URI url;
    private OffsetDateTime updatedAt;
    private OffsetDateTime checkedAt;
    private Chat chat;
}
