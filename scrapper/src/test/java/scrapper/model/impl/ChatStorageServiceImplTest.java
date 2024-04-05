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
import scrapper.domain.entity.Chat;
import scrapper.domain.entity.Link;
import scrapper.model.dto.ChatDTO;
import scrapper.model.dto.LinkDTO;
import scrapper.model.dto.MapperEntityWithDTO;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ChatStorageServiceImplTest {
    private ChatRepository chatRepository;
    private MapperEntityWithDTO mapper;
    private ChatStorageServiceImpl chatStorageService;


    @BeforeEach
    void init() {
        chatRepository = Mockito.mock(ChatRepository.class);
        mapper = new MapperEntityWithDTO();

        chatStorageService = new ChatStorageServiceImpl(chatRepository, mapper);
    }

    @Test
    void addChat_testCorrectLogic() {
        Chat ret = new Chat();
        ret.setChatId(111L);
        Mockito.when(chatRepository.add(Mockito.any()))
                .thenReturn(ret);

        ChatDTO chatDTO = mapper.getChatDto(ret);
        chatStorageService.addChat(chatDTO);


        Chat actual = mapper.getChat(chatDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals(actual.getId(), ret.getId()),
                () -> Assertions.assertEquals(actual.getChatId(), ret.getChatId())
        );

        Mockito.verify(chatRepository, Mockito.times(1))
                .add(Mockito.any());
    }

    @Test
    void removeChat_testCorrectLogic() {
        ChatDTO chatDTO = new ChatDTO(1L);
        chatStorageService.removeChat(chatDTO);

        Mockito.verify(chatRepository, Mockito.times(1))
                .remove(ArgumentMatchers.eq(mapper.getChat(chatDTO)));
    }

    @Test
    void findAllChatsByCurrentUrl_testCorrectLogic() {
        List<Chat> chats = List.of(
                new Chat(),
                new Chat(),
                new Chat()
        );

        Mockito.when(chatRepository.findAllChatWhatLinkUrlIs(Mockito.any(), Mockito.any()))
                .thenReturn(new PageImpl<>(chats));

        String url = "ahaha";
        PageRequest request = PageRequest.of(1, 1);
        Page<ChatDTO> actual = chatStorageService.findAllChatsByCurrentUrl(url, request);

        Assertions.assertEquals(actual.getContent(), mapper.getChatDtoList(chats));
        Mockito.verify(chatRepository, Mockito.times(1))
                .findAllChatWhatLinkUrlIs(ArgumentMatchers.eq(url), ArgumentMatchers.eq(request));
    }

    @Test
    void findAllLinksByChatId_testCorrectLogic() {
        Chat chat = new Chat();
        chat.setId(111L);

        List<Link> list = List.of(new Link(), new Link(), new Link());
        list.forEach(link -> link.setChat(chat));

        chat.setLinks(list);
        Mockito.when(chatRepository.findByChatId(Mockito.anyLong()))
                .thenReturn(chat);

        List<LinkDTO> actual = chatStorageService.findAllLinksByChatId(1L);

        Assertions.assertEquals(list.size(), actual.size());

        Mockito.verify(chatRepository, Mockito.times(1))
                .findByChatId(Mockito.anyLong());
    }

    @Test
    void findAllLinksByChatId_testWhenNoLinks() {
        Chat chat = new Chat();
        chat.setLinks(null);
        Mockito.when(chatRepository.findByChatId(Mockito.anyLong()))
                .thenReturn(chat);

        Assertions.assertThrows(ClientException.class, () -> chatStorageService.findAllLinksByChatId(1L));

        Mockito.verify(chatRepository, Mockito.times(1))
                .findByChatId(Mockito.anyLong());
    }
}