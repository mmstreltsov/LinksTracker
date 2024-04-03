package scrapper.model.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import scrapper.domain.ChatRepository;
import scrapper.model.dto.ChatDTO;
import scrapper.model.dto.LinkDTO;
import scrapper.model.dto.MapperEntityWithDTO;
import scrapper.model.entity.Chat;
import scrapper.model.entity.Link;

import java.net.URI;
import java.time.OffsetDateTime;
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

//    @Test
//    void addUser_testCorrectLogic() {
//        Chat ret = new Chat(1L, 2L, 3L);
//        Mockito.when(chatRepository.addChat(Mockito.any()))
//                .thenReturn(ret);
//
//        ChatDTO chatDTO = chatStorageService.addUser(mapper.getChatDto(ret));
//        Chat actual = mapper.getChat(chatDTO);
//
//        Assertions.assertAll(
//                () -> Assertions.assertEquals(actual.getId(), ret.getId()),
//                () -> Assertions.assertEquals(actual.getChatId(), ret.getChatId()),
//                () -> Assertions.assertEquals(actual.getLinkId(), ret.getLinkId())
//        );
//
//        Mockito.verify(chatRepository, Mockito.times(1))
//                .addChat(Mockito.any());
//    }
//
//    @Test
//    void removeEveryRowForUser_testCorrectLogic() {
//        Long id = -1L;
//        chatStorageService.removeEveryRowForUser(id);
//
//        Mockito.verify(chatRepository, Mockito.times(1))
//                .removeChatByChatId(ArgumentMatchers.eq(id));
//    }
//
//    @Test
//    void removeByChatIdAndLinkId_testCorrectLogic() {
//        Chat chat = new Chat(-1L, 2L, 5L);
//        Mockito.when(chatRepository.findChatByChatIdAndLinkId(Mockito.anyLong(), Mockito.anyLong()))
//                .thenReturn(chat);
//
//        chatStorageService.removeByChatIdAndLinkId(chat.getChatId(), chat.getLinkId());
//
//        Mockito.verify(chatRepository, Mockito.times(1))
//                .findChatByChatIdAndLinkId(ArgumentMatchers.eq(chat.getChatId()), ArgumentMatchers.eq(chat.getLinkId()));
//        Mockito.verify(chatRepository, Mockito.times(1))
//                .removeChatById(ArgumentMatchers.eq(chat.getId()));
//    }
//
//    @Test
//    void findAllChatsByCurrentUrl_testCorrectLogic() {
//        List<Chat> chats = List.of(new Chat(1L, 2L, 4L), new Chat(2L, 4L, 8L));
//
//        Mockito.when(chatRepository.findAllByCurrentLinkUrl(Mockito.any()))
//                .thenReturn(chats);
//
//        String url = "ahaha";
//        List<ChatDTO> actual = chatStorageService.findAllChatsByCurrentUrl(url);
//
//        Assertions.assertEquals(actual, mapper.getChatDtoList(chats));
//        Mockito.verify(chatRepository, Mockito.times(1))
//                .findAllByCurrentLinkUrl(ArgumentMatchers.eq(url));
//    }
//
//    @Test
//    void findAllLinksByChatId_testCorrectLogic() {
//        List<Link> links = List.of(
//                new Link(1L, URI.create("1L"), OffsetDateTime.MAX, OffsetDateTime.MIN),
//                new Link(2L, URI.create("-1L"), OffsetDateTime.MAX, OffsetDateTime.MAX),
//                new Link(3L, URI.create("+1L"), OffsetDateTime.MIN, OffsetDateTime.MIN)
//        );
//
//        Mockito.when(chatRepository.findAllLinksByChatId(Mockito.any()))
//                .thenReturn(links);
//
//        Long id = 7L;
//        List<LinkDTO> actual = chatStorageService.findAllLinksByChatId(id);
//
//        Assertions.assertEquals(actual, mapper.getLinkDtoList(links));
//        Mockito.verify(chatRepository, Mockito.times(1))
//                .findAllLinksByChatId(ArgumentMatchers.eq(id));
//    }
}