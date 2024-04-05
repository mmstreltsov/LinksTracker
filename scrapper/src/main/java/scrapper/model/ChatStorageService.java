package scrapper.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import scrapper.model.dto.ChatDTO;
import scrapper.model.dto.LinkDTO;

import java.util.List;

public interface ChatStorageService {
    void addChat(ChatDTO chatDTO);

    void removeChat(ChatDTO chatDTO);

    Page<ChatDTO> findAllChatsByCurrentUrl(String url, Pageable pageable);
    List<LinkDTO> findAllLinksByChatId(Long chatId);
}
