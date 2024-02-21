package bot.client.dto;

import lombok.Builder;
import lombok.ToString;

import java.net.URI;

@Builder
public class AddLinkRequest {
    public URI link;
}
