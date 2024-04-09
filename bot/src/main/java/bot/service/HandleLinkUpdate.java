package bot.service;

import bot.dto.LinkUpdateRequest;

public interface HandleLinkUpdate {
    void handleLinkUpdate(LinkUpdateRequest request);
}
