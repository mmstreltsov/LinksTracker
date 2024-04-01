package scrapper.model.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import scrapper.domain.ChatRepository;
import scrapper.model.ChatStorageService;
import scrapper.model.dto.ChatDTO;
import scrapper.model.dto.LinkDTO;
import scrapper.model.dto.MapperEntityToDTO;
import scrapper.model.entity.Chat;

import java.util.List;

@Service
public class ChatStorageServiceImpl implements ChatStorageService {

    private final ChatRepository chatRepository;
    private final MapperEntityToDTO mapper;

    public ChatStorageServiceImpl(@Qualifier("jdbcChatRepository") ChatRepository chatRepository,
                                  MapperEntityToDTO mapper) {
        this.chatRepository = chatRepository;
        this.mapper = mapper;
    }

    @Override
    public ChatDTO addUser(ChatDTO chatDTO) {
        Long id = chatRepository.addChatAndGetID(mapper.getChat(chatDTO));
        return ChatDTO.builder()
                .id(id)
                .chatId(chatDTO.getChatId())
                .linkId(chatDTO.getLinkId())
                .build();
    }

    @Override
    public List<ChatDTO> findAllChatsByChatId(Long id) {
        return mapper.getChatDtoList(chatRepository.findAllByChatId(id));
    }

    @Override
    public List<ChatDTO> findAllChatsByCurrentUrl(String url) {
        return mapper.getChatDtoList(chatRepository.findAllByCurrentLinkUrl(url));
    }

    @Override
    public List<LinkDTO> findAllLinksByChatId(Long id) {
        return mapper.getLinkDtoList(chatRepository.findAllLinksByChatId(id));
    }


    @Override
    public void removeEveryRowForUser(Long chatId) {
        ChatDTO chatDTO = ChatDTO.builder().chatId(chatId).build();
        chatRepository.removeEveryChatByChatId(mapper.getChat(chatDTO));
    }

    @Override
    public void removeByChatIdAndLinkId(Long chatId, Long linkId) {
        Chat chat = chatRepository.findChatByChatIdAndLinkId(chatId, linkId);
        if (chat != null) {
            chatRepository.removeChatById(chat.getId());
        }
    }
}
