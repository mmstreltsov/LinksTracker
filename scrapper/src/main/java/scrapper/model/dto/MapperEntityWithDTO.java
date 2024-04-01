package scrapper.model.dto;

import org.springframework.stereotype.Component;
import scrapper.model.entity.Chat;
import scrapper.model.entity.Link;

import java.util.List;

@Component
public class MapperEntityWithDTO {


    public ChatDTO getChatDto(Chat chat) {
        return ChatDTO.builder()
                .id(chat.getId())
                .chatId(chat.getChatId())
                .linkId(chat.getLinkId())
                .build();
    }

    public List<ChatDTO> getChatDtoList(List<Chat> chatList) {
        return chatList.stream().map(this::getChatDto).toList();
    }

    public LinkDTO getLinkDto(Link link) {
        return LinkDTO.builder()
                .id(link.getId())
                .url(link.getUrl())
                .checkedAt(link.getCheckedAt())
                .updatedAt(link.getUpdatedAt())
                .build();
    }

    public List<LinkDTO> getLinkDtoList(List<Link> linkList) {
        return linkList.stream().map(this::getLinkDto).toList();
    }

    public Chat getChat(ChatDTO chat) {
        return Chat.builder()
                .id(chat.getId())
                .chatId(chat.getChatId())
                .linkId(chat.getLinkId())
                .build();
    }

    public Link getLink(LinkDTO link) {
        return Link.builder()
                .id(link.getId())
                .url(link.getUrl())
                .checkedAt(link.getCheckedAt())
                .updatedAt(link.getUpdatedAt())
                .build();
    }
}
