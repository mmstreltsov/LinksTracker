package bot.client.dto;

import lombok.ToString;

import java.util.List;
@ToString
public class ListLinksResponse {
    public List<LinkResponse> links;
    public Integer size;
}
