package scrapper.model.dto;

import org.springframework.stereotype.Component;
import scrapper.domain.entity.Chat;
import scrapper.domain.entity.Link;

import java.util.List;

@Component
public class MapperEntityWithDTO {

    public ChatDTO getChatDto(Chat chat) {
        return ChatDTO.builder()
                .chatId(chat.getChatId())
                .build();
    }

    public List<ChatDTO> getChatDtoList(List<Chat> chatList) {
        if (chatList == null) {
            return null;
        }
        return chatList.stream().map(this::getChatDto).toList();
    }

    public LinkDTO getLinkDto(Link link) {
        return LinkDTO.builder()
                .url(link.getUrl())
                .updatedAt(link.getUpdatedAt())
                .chat(this.getChatDto(link.getChat()))
                .build();
    }

    public List<LinkDTO> getLinkDtoList(List<Link> linkList) {
        if (linkList == null) {
            return null;
        }
        return linkList.stream().map(this::getLinkDto).toList();
    }

    public List<Link> getLinkList(List<LinkDTO> linkList) {
        if (linkList == null) {
            return null;
        }
        return linkList.stream().map(this::getLink).toList();
    }

    public Chat getChat(ChatDTO chat) {
        return Chat.builder()
                .chatId(chat.getChatId())
                .build();
    }

    public Link getLink(LinkDTO link) {
        return Link.builder()
                .url(link.getUrl())
                .updatedAt(link.getUpdatedAt())
                .chat(this.getChat(link.getChat()))
                .build();
    }
}
