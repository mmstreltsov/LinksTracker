package scrapper.model.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import scrapper.domain.ChatRepository;
import scrapper.domain.LinkRepository;
import scrapper.model.LinkStorageService;
import scrapper.model.entity.Chat;
import scrapper.model.entity.Link;

import java.util.List;

@Service
public class LinkStorageServiceImpl implements LinkStorageService {

    private final ChatRepository chatRepository;
    private final LinkRepository linkRepository;

    public LinkStorageServiceImpl(@Qualifier("jdbcChatRepository") ChatRepository chatRepository,
                                  @Qualifier("jdbcLinkRepository") LinkRepository linkRepository) {
        this.chatRepository = chatRepository;
        this.linkRepository = linkRepository;
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
    public Link addLink(Link link) {
        Long id = linkRepository.addLinkAndGetID(link);
        return Link.builder()
                .id(id)
                .url(link.getUrl())
                .build();
    }

    @Override
    public void removeUser(Long chatId) {
        chatRepository.removeChat(Chat.builder().chatId(chatId).build());
    }

    @Override
    public void removeLink(Link link) {
        linkRepository.removeLink(link);
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
    public Link findLinkById(Long id) {
        return linkRepository.findById(id);
    }
}
