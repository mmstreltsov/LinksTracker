package scrapper.model.dto;

import org.springframework.stereotype.Component;
import scrapper.model.entity.Chat;
import scrapper.model.entity.Link;

import java.net.URI;
import java.util.List;

@Component
public class MapperEntityWithDTO {

    public ChatDTO getChatDto(Chat chat) {
        return ChatDTO.builder()
                .id(chat.getId())
                .chatId(chat.getChatId())
                .links(this.getLinkDtoList(chat.getLinks()))
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
                .id(link.getId())
                .chat(link.getChat())
                .url(URI.create(link.getUrl()))
                .checkedAt(link.getCheckedAt())
                .updatedAt(link.getUpdatedAt())
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
                .id(chat.getId())
                .chatId(chat.getChatId())
                .links(this.getLinkList(chat.getLinks()))
                .build();
    }

    public Link getLink(LinkDTO link) {
        return Link.builder()
                .id(link.getId())
                .chat(link.getChat())
                .url(link.getUrl().toString())
                .checkedAt(link.getCheckedAt())
                .updatedAt(link.getUpdatedAt())
                .build();
    }
}
