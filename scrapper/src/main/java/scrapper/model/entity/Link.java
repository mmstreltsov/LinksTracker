package scrapper.model.entity;

import lombok.Builder;
import lombok.Data;

import java.net.URI;

@Data
@Builder
public class Link {
    private Long id;
    private URI url;
}
