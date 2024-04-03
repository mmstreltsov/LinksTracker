package scrapper.model.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import scrapper.domain.LinkRepository;
import scrapper.model.dto.MapperEntityWithDTO;

@ExtendWith(MockitoExtension.class)
class LinkStorageServiceImplTest {
    private LinkRepository linkRepository;
    private MapperEntityWithDTO mapper;
    private LinkStorageServiceImpl linkStorageService;

    @BeforeEach
    void init() {
        linkRepository = Mockito.mock(LinkRepository.class);
        mapper = new MapperEntityWithDTO();

        linkStorageService = new LinkStorageServiceImpl(linkRepository, mapper);
    }

//    @Test
//    void addLink_testCorrectLogic() {
//        Link ret = new Link(1L, URI.create("2L"), OffsetDateTime.now(), OffsetDateTime.MIN);
//        Mockito.when(linkRepository.addLink(Mockito.any()))
//                .thenReturn(ret);
//
//        LinkDTO linkDTO = linkStorageService.addLink(mapper.getLinkDto(ret));
//        Link actual = mapper.getLink(linkDTO);
//
//        Assertions.assertAll(
//                () -> Assertions.assertEquals(actual.getId(), ret.getId()),
//                () -> Assertions.assertEquals(actual.getUrl(), ret.getUrl()),
//                () -> Assertions.assertEquals(actual.getCheckedAt(), ret.getCheckedAt()),
//                () -> Assertions.assertEquals(actual.getUpdatedAt(), ret.getUpdatedAt())
//        );
//
//        Mockito.verify(linkRepository, Mockito.times(1))
//                .addLink(Mockito.any());
//    }
//
//    @Test
//    void removeLink_testCorrectLogic() {
//        LinkDTO linkDTO = new LinkDTO(1L, URI.create("2L"), OffsetDateTime.now(), OffsetDateTime.MIN);
//
//        linkStorageService.removeLink(linkDTO);
//
//        Mockito.verify(linkRepository, Mockito.times(1))
//                .removeLink(Mockito.any());
//        Mockito.verify(linkRepository).removeLink(
//                ArgumentMatchers.argThat((t) -> mapper.getLinkDto(t).equals(linkDTO))
//        );
//    }
//
//    @Test
//    void setCheckFieldToNow_testCorrectLogic() {
//        LinkDTO linkDTO = new LinkDTO(1L, URI.create("2L"), OffsetDateTime.now(), OffsetDateTime.MIN);
//
//        linkStorageService.setCheckFieldToNow(linkDTO);
//
//        Mockito.verify(linkRepository, Mockito.times(1))
//                .updateCheckField(ArgumentMatchers.argThat((t) -> mapper.getLinkDto(t).equals(linkDTO)));
//    }
//
//    @Test
//    void setUpdateFieldToNow_testCorrectLogic() {
//        LinkDTO linkDTO = new LinkDTO(1L, URI.create("2L"), OffsetDateTime.now(), OffsetDateTime.MIN);
//
//        linkStorageService.setUpdateFieldToNow(linkDTO);
//
//        Mockito.verify(linkRepository, Mockito.times(1))
//                .updateUpdateField(ArgumentMatchers.argThat((t) -> mapper.getLinkDto(t).equals(linkDTO)));
//    }
//
//    @Test
//    void findLinksCheckedFieldLessThenGivenAndUniqueUrl_testCorrectLogic() {
//        List<Link> links = List.of(
//                new Link(1L, URI.create("2L"), OffsetDateTime.now(), OffsetDateTime.MIN),
//                new Link(2L, URI.create("1L"), OffsetDateTime.MAX, OffsetDateTime.MIN),
//                new Link(3L, URI.create("3L"), OffsetDateTime.now(), OffsetDateTime.MAX)
//        );
//        Mockito.when(linkRepository.findLinksCheckedFieldLessThenGivenAndUniqueUrl(Mockito.any()))
//                        .thenReturn(links);
//
//        OffsetDateTime dateTime = OffsetDateTime.now();
//        List<LinkDTO> actual = linkStorageService.findLinksCheckedFieldLessThenGivenAndUniqueUrl(dateTime);
//
//        Assertions.assertEquals(mapper.getLinkDtoList(links), actual);
//        Mockito.verify(linkRepository, Mockito.times(1))
//                .findLinksCheckedFieldLessThenGivenAndUniqueUrl(ArgumentMatchers.eq(dateTime));
//    }
}