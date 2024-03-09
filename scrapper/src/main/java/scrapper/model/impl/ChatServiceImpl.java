package scrapper.model.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import scrapper.domain.ChatRepository;
import scrapper.model.ChatService;
import scrapper.model.entity.Chat;
import scrapper.model.entity.Link;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    public ChatServiceImpl(@Qualifier("jdbcChatRepository") ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public Chat addUser(Chat chat) {
        Long id = chatRepository.addChatAndGetID(chat);
        return Chat.builder()
                .id(id)
                .chatId(chat.getChatId())
                .linkId(chat.getLinkId())
                .build();
    }

    @Override
    public List<Chat> findAllChatsByChatId(Long id) {
        return chatRepository.findAllByChatId(id);
    }

    @Override
    public List<Link> findAllLinksByChatId(Long id) {
        return chatRepository.findAllLinksByChatId(id);
    }


    @Override
    public void removeUser(Long chatId) {
        chatRepository.removeChat(Chat.builder().chatId(chatId).build());
    }

    @Override
    public void removeByChatIdAndLinkId(Long chatId, Long linkId) {
        Chat chat = chatRepository.findChatByChatIdAndLinkId(chatId, linkId);
        chatRepository.removeChat(chat);
    }
}
