package scrapper.client.botClient;

import scrapper.model.dto.ChatDTO;
import scrapper.model.dto.LinkDTO;

import java.util.List;

public interface BotClient {
    void updateLink(List<ChatDTO> chatDTO, LinkDTO linkDTO);
}
