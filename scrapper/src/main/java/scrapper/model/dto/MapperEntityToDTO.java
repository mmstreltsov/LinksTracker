package scrapper.model.dto;

import org.springframework.stereotype.Component;
import scrapper.model.entity.Chat;
import scrapper.model.entity.Link;

import java.util.List;

@Component
public class MapperEntityToDTO {


    public ChatDTO getChatDto(Chat chat) {
        return ChatDTO.builder()
                .chatId(chat.getId())
                .id(chat.getId())
                .linkId(chat.getLinkId())
                .build();
    }

    public List<ChatDTO> getChatDtoList(List<Chat> chatList) {
        return chatList.stream().map(this::getChatDto).toList();
    }

    public LinkDTO getLinkDto(Link link) {
        return LinkDTO.builder()
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
                .chatId(chat.getId())
                .id(chat.getId())
                .linkId(chat.getLinkId())
                .build();
    }

    public Link getLink(LinkDTO link) {
        return Link.builder()
                .url(link.getUrl())
                .checkedAt(link.getCheckedAt())
                .updatedAt(link.getUpdatedAt())
                .build();
    }
}
