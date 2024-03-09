package scrapper.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.net.URI;

@Data
@Builder
@AllArgsConstructor
public class Link {
    private Long id;
    private URI url;
}
