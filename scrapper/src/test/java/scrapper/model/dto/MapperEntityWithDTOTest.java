package scrapper.model.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import scrapper.domain.entity.Chat;
import scrapper.domain.entity.Link;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

class MapperEntityWithDTOTest {

    private final MapperEntityWithDTO mapper = new MapperEntityWithDTO();

    @Test
    void getChat_testCorrectLogic() {
        ChatDTO chatDTO = new ChatDTO(1L, 2L, null);

        Chat chat = mapper.getChat(chatDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals(chat.getId(), chatDTO.getId()),
                () -> Assertions.assertEquals(chat.getChatId(), chatDTO.getChatId()),
                () -> Assertions.assertEquals(mapper.getLinkDtoList(chat.getLinks()), chatDTO.getLinks())
        );
    }

    @Test
    void getChatDTO_testCorrectLogic() {
        Chat chat = new Chat(1L, 2L, null);

        ChatDTO chatDTO = mapper.getChatDto(chat);

        Assertions.assertAll(
                () -> Assertions.assertEquals(chatDTO.getId(), chat.getId()),
                () -> Assertions.assertEquals(chatDTO.getChatId(), chat.getChatId()),
                () -> Assertions.assertEquals(mapper.getLinkList(chatDTO.getLinks()), chat.getLinks())
        );
    }

    @Test
    void getChatDTOList_testCorrectLogic() {
        Chat chat = new Chat(1L, 2L, null);
        List<Chat> chatList = List.of(chat, chat, chat);

        List<ChatDTO> chatDTOList = mapper.getChatDtoList(chatList);
        Assertions.assertEquals(chatDTOList.size(), chatList.size());
        for (int i = 0; i < chatList.size(); ++i) {
            Chat c = chatList.get(i);
            ChatDTO cDTO = chatDTOList.get(i);


            Assertions.assertAll(
                    () -> Assertions.assertEquals(c.getId(), cDTO.getId()),
                    () -> Assertions.assertEquals(c.getChatId(), cDTO.getChatId()),
                    () -> Assertions.assertEquals(mapper.getLinkDtoList(c.getLinks()), cDTO.getLinks())
            );
        }
    }

    @Test
    void getLink_testCorrectLogic() {
        LinkDTO linkDTO = new LinkDTO(1L, URI.create("1L"), OffsetDateTime.MAX, OffsetDateTime.MIN, null);

        Link link = mapper.getLink(linkDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals(link.getId(), linkDTO.getId()),
                () -> Assertions.assertEquals(link.getUrl(), linkDTO.getUrl()),
                () -> Assertions.assertEquals(link.getUpdatedAt(), linkDTO.getUpdatedAt()),
                () -> Assertions.assertEquals(link.getCheckedAt(), linkDTO.getCheckedAt())
        );
    }

    @Test
    void getLinkDTO_testCorrectLogic() {
        Link link = new Link(1L, "1L", OffsetDateTime.MAX, OffsetDateTime.MIN, null);

        LinkDTO linkDTO = mapper.getLinkDto(link);

        Assertions.assertAll(
                () -> Assertions.assertEquals(link.getId(), linkDTO.getId()),
                () -> Assertions.assertEquals(link.getUrl(), linkDTO.getUrl()),
                () -> Assertions.assertEquals(link.getUpdatedAt(), linkDTO.getUpdatedAt()),
                () -> Assertions.assertEquals(link.getCheckedAt(), linkDTO.getCheckedAt())
        );
    }

    @Test
    void getLinkDTOList_testCorrectLogic() {
        Link link = new Link(1L, "1L", OffsetDateTime.MAX, OffsetDateTime.MIN, null);
        List<Link> linkList = List.of(link, link, link);

        List<LinkDTO> linkDTOList = mapper.getLinkDtoList(linkList);
        Assertions.assertEquals(linkDTOList.size(), linkList.size());
        for (int i = 0; i < linkList.size(); ++i) {
            Link l = linkList.get(i);
            LinkDTO lDTO = linkDTOList.get(i);


            Assertions.assertAll(
                    () -> Assertions.assertEquals(l.getId(), lDTO.getId()),
                    () -> Assertions.assertEquals(l.getUrl(), lDTO.getUrl()),
                    () -> Assertions.assertEquals(l.getUpdatedAt(), lDTO.getUpdatedAt()),
                    () -> Assertions.assertEquals(l.getCheckedAt(), lDTO.getCheckedAt())
            );
        }
    }
}