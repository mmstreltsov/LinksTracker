package bot.client.dto;

import lombok.Builder;

import java.net.URI;

@Builder
public class RemoveLinkRequest {
    public URI link;
}
