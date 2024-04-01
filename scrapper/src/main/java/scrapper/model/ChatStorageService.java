package scrapper.model;

import scrapper.model.dto.ChatDTO;
import scrapper.model.dto.LinkDTO;

import java.util.List;

public interface ChatStorageService {
    ChatDTO addUser(ChatDTO chatDTO);

    void removeEveryRowForUser(Long chatId);

    void removeByChatIdAndLinkId(Long chatId, Long linkId);

    List<ChatDTO> findAllChatsByChatId(Long id);

    List<ChatDTO> findAllChatsByCurrentUrl(String url);

    List<LinkDTO> findAllLinksByChatId(Long id);
}
