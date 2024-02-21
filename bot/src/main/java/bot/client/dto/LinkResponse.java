package bot.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.net.URI;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LinkResponse {
    public Long id;
    public URI url;

    @Override
    public String toString() {
        return url.toString();
    }
}
