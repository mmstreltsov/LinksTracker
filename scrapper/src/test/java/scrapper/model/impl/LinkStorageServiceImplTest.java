package scrapper.model.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import scrapper.controllers.errors.ClientException;
import scrapper.domain.ChatRepository;
import scrapper.domain.LinkRepository;
import scrapper.domain.entity.Chat;
import scrapper.domain.entity.Link;
import scrapper.model.dto.ChatDTO;
import scrapper.model.dto.LinkDTO;
import scrapper.model.dto.MapperEntityWithDTO;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class LinkStorageServiceImplTest {
    private LinkRepository linkRepository;
    private ChatRepository chatRepository;
    private MapperEntityWithDTO mapper;
    private LinkStorageServiceImpl linkStorageService;

    @BeforeEach
    void init() {
        linkRepository = Mockito.mock(LinkRepository.class);
        chatRepository = Mockito.mock(ChatRepository.class);
        mapper = new MapperEntityWithDTO();

        linkStorageService = new LinkStorageServiceImpl(linkRepository, chatRepository, mapper);
    }

    @Test
    void addLink_testValidationFailChatIsNull() {
        ChatDTO chatDTO = new ChatDTO(1L);
        LinkDTO linkDTO = new LinkDTO("a", OffsetDateTime.MIN, chatDTO);

        Mockito.when(chatRepository.findById(Mockito.anyLong()))
                .thenReturn(null);

        Assertions.assertThrows(ClientException.class, () -> linkStorageService.addLink(linkDTO));
    }

    @Test
    void addLink_testValidationFailChatContainsTooManyLinks() {
        ChatDTO chatDTO = new ChatDTO(1L);
        LinkDTO linkDTO = new LinkDTO("a", OffsetDateTime.MIN, chatDTO);

        Chat chat = Mockito.mock(Chat.class);
        List list = Mockito.mock(List.class);
        Mockito.when(chatRepository.findById(Mockito.anyLong()))
                .thenReturn(chat);
        Mockito.when(chat.getLinks())
                .thenReturn(list);
        Mockito.when(list.size())
                .thenReturn(1025);

        Assertions.assertThrows(ClientException.class, () -> linkStorageService.addLink(linkDTO));
    }

    @Test
    void addLink_testValidationFailUrlIsTooLong() {
        ChatDTO chatDTO = new ChatDTO(1L);
        StringBuilder longString = new StringBuilder();
        for (int i = 0; i < 1025; i++) {
            longString.append("a");
        }
        LinkDTO linkDTO = new LinkDTO(longString.toString(), OffsetDateTime.MIN, chatDTO);

        Chat chat = new Chat();
        chat.setLinks(new ArrayList<>());
        Mockito.when(chatRepository.findById(Mockito.anyLong()))
                .thenReturn(chat);

        Assertions.assertThrows(ClientException.class, () -> linkStorageService.addLink(linkDTO));
    }

    @Test
    void addLink_testValidationFailUrlIsEmpty() {
        ChatDTO chatDTO = new ChatDTO(1L);
        LinkDTO linkDTO = new LinkDTO("", OffsetDateTime.MIN, chatDTO);

        Chat chat = new Chat();
        chat.setLinks(new ArrayList<>());
        Mockito.when(chatRepository.findById(Mockito.anyLong()))
                .thenReturn(chat);

        Assertions.assertThrows(ClientException.class, () -> linkStorageService.addLink(linkDTO));
    }

    @Test
    void addLink_testCorrectLogic() {
        ChatDTO chatDTO = new ChatDTO(1L);
        LinkDTO linkDTO = new LinkDTO("ahaha", OffsetDateTime.MIN, chatDTO);

        Chat chat = new Chat();
        chat.setLinks(new ArrayList<>());
        Mockito.when(chatRepository.findById(Mockito.anyLong()))
                .thenReturn(chat);

        linkStorageService.addLink(linkDTO);

        Mockito.verify(linkRepository, Mockito.times(1))
                .add(ArgumentMatchers.eq(mapper.getLink(linkDTO)));
    }

    @Test
    void removeLink_testCorrectLogic() {
        ChatDTO chatDTO = new ChatDTO(1L);
        LinkDTO linkDTO = new LinkDTO("ahaha", OffsetDateTime.MIN, chatDTO);

        Link link = Mockito.mock(Link.class);
        Mockito.when(linkRepository.findByUlrAndChatId(Mockito.anyString(), Mockito.anyLong()))
                .thenReturn(link);

        linkStorageService.removeLink(linkDTO);

        Mockito.verify(linkRepository, Mockito.times(1))
                .remove(ArgumentMatchers.eq(link));
    }

    @Test
    void setCheckFieldToNow_testCorrectLogic() {
        ChatDTO chatDTO = new ChatDTO(1L);
        LinkDTO linkDTO = new LinkDTO("ahaha", OffsetDateTime.MIN, chatDTO);

        Link link = Mockito.mock(Link.class);
        Mockito.when(linkRepository.findByUlrAndChatId(Mockito.anyString(), Mockito.anyLong()))
                .thenReturn(link);

        linkStorageService.setCheckFieldToNow(linkDTO);

        Mockito.verify(link, Mockito.times(1))
                .setCheckedAt(Mockito.any(OffsetDateTime.class));
        Mockito.verify(linkRepository, Mockito.times(1))
                .update(ArgumentMatchers.eq(link));
    }

    @Test
    void setUpdateFieldToNow_testCorrectLogic() {
        ChatDTO chatDTO = new ChatDTO(1L);
        LinkDTO linkDTO = new LinkDTO("ahaha", OffsetDateTime.MIN, chatDTO);

        Link link = Mockito.mock(Link.class);
        Mockito.when(linkRepository.findByUlrAndChatId(Mockito.anyString(), Mockito.anyLong()))
                .thenReturn(link);

        linkStorageService.setUpdateFieldToNow(linkDTO);

        Mockito.verify(link, Mockito.times(1))
                .setUpdatedAt(Mockito.any(OffsetDateTime.class));
        Mockito.verify(linkRepository, Mockito.times(1))
                .update(ArgumentMatchers.eq(link));
    }

    @Test
    void findLinksCheckedFieldLessThenGivenAndUniqueUrl_testCorrectLogic() {
        Chat chat = new Chat();
        chat.setId(1L);
        chat.setChatId(1L);
        List<Link> links = List.of(
                new Link(1L, "ahaha", OffsetDateTime.MIN, OffsetDateTime.MIN, chat),
                new Link(2L, "ahaha", OffsetDateTime.MIN, OffsetDateTime.MIN, chat),
                new Link(3L, "ahaha", OffsetDateTime.MIN, OffsetDateTime.MIN, chat)
        );
        PageImpl<Link> page = new PageImpl<>(links, PageRequest.of(1, 1), 5);

        Mockito.when(linkRepository.findUniqueUrlWhatNotCheckedForALongTime(Mockito.anyInt(), Mockito.any(), Mockito.any()))
                .thenReturn(page);

        Page<LinkDTO> actual = linkStorageService.findUniqueUrlWhatNotCheckedForALongTime(1, ChronoUnit.MINUTES, PageRequest.of(1, 2));

        Assertions.assertEquals(mapper.getLinkDtoList(links), actual.getContent());
    }
}