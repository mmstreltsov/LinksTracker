package bot.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListLinksResponse {
    public List<LinkResponse> links;
    public Integer size;

    @Override
    public String toString() {
        return links.toString();
    }
}
