package scrapper.client.botClient.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class LinkUpdateRequest {
    public Long id;
    public String url;
    public String description;
    public List<Long> tgChatIds;
}
