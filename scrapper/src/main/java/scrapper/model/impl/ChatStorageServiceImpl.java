package scrapper.model.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import scrapper.domain.ChatRepository;
import scrapper.domain.entity.Chat;
import scrapper.domain.entity.Link;
import scrapper.model.ChatStorageService;
import scrapper.model.dto.ChatDTO;
import scrapper.model.dto.LinkDTO;
import scrapper.model.dto.MapperEntityWithDTO;

import java.util.List;

@Service
public class ChatStorageServiceImpl implements ChatStorageService {

    private final ChatRepository chatRepository;
    private final MapperEntityWithDTO mapper;

    public ChatStorageServiceImpl(ChatRepository chatRepository,
                                  MapperEntityWithDTO mapper) {
        this.chatRepository = chatRepository;
        this.mapper = mapper;
    }

    @Override
    public void addChat(ChatDTO chatDTO) {
        chatRepository.add(mapper.getChat(chatDTO));
    }

    @Override
    public void removeChat(ChatDTO chatDTO) {
        chatRepository.remove(mapper.getChat(chatDTO));
    }

    @Override
    public Page<ChatDTO> findAllChatsByCurrentUrl(String url, Pageable pageable) {
        Page<Chat> page = chatRepository.findAllChatWhatLinkUrlIs(url, pageable);
        return page.map(mapper::getChatDto);
    }

    @Override
    public List<LinkDTO> findAllLinksByChatId(Long chatId) {
        List<Link> links = chatRepository.findByChatId(chatId).getLinks();
        if (links != null) {
            return links.stream().map(mapper::getLinkDto).toList();
        }
        return null;
    }
}
