package bot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@AllArgsConstructor
@Builder
public class LinkUpdateRequest {
    public Long id;
    public String url;
    public String description;
    public List<Long> tgChatIds;
}
