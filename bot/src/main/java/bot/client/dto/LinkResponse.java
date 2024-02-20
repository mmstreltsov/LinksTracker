package bot.client.dto;

import lombok.ToString;

import java.net.URI;
@ToString
public class LinkResponse {
    public Long id;
    public URI url;
}
