package bot.dto;

import java.util.List;

public class LinkUpdateRequest {
    public Long id;
    public String url;
    public String description;
    public List<Long> tgChatIds;
}
