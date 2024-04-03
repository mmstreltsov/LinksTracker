package scrapper.model.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import scrapper.domain.ChatRepository;
import scrapper.model.ChatStorageService;
import scrapper.model.dto.ChatDTO;
import scrapper.model.dto.LinkDTO;
import scrapper.model.dto.MapperEntityWithDTO;
import scrapper.domain.entity.Chat;

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
    public ChatDTO addUser(ChatDTO chatDTO) {
//        Chat chat = chatRepository.addChat(mapper.getChat(chatDTO));
//        return mapper.getChatDto(chat);
        return null;
    }

    @Override
    public List<ChatDTO> findAllChatsByCurrentUrl(String url) {
//        return mapper.getChatDtoList(chatRepository.findAllByCurrentLinkUrl(url));
        return null;
    }

    @Override
    public List<LinkDTO> findAllLinksByChatId(Long id) {
//        return mapper.getLinkDtoList(chatRepository.findAllLinksByChatId(id));
        return null;
    }


    @Override
    public void removeEveryRowForUser(Long chatId) {
//        chatRepository.removeChatByChatId(chatId);
    }

    @Override
    public void removeByChatIdAndLinkId(Long chatId, Long linkId) {
//        Chat chat = chatRepository.findChatByChatIdAndLinkId(chatId, linkId);
//        if (chat != null) {
//            chatRepository.removeChatById(chat.getId());
//        }
    }
}
