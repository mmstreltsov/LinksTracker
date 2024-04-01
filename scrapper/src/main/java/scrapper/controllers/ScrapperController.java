package scrapper.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import scrapper.controllers.dto.AddLinkRequest;
import scrapper.controllers.dto.LinkResponse;
import scrapper.controllers.dto.ListLinksResponse;
import scrapper.controllers.dto.RemoveLinkRequest;
import scrapper.controllers.errors.ClientException;
import scrapper.model.ChatStorageService;
import scrapper.model.LinkStorageService;
import scrapper.model.dto.ChatDTO;
import scrapper.model.dto.LinkDTO;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@RestController
public class ScrapperController {

    private LinkStorageService linkStorageService;
    private ChatStorageService chatStorageService;

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<Object> registerChat(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<Object> deleteChat(@PathVariable("id") Long id) {
        chatStorageService.removeEveryRowForUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @GetMapping("/links")
    public ResponseEntity<Object> getLinks(@RequestHeader("Tg-Chat-Id") Long chatId) {
        List<LinkDTO> chats = chatStorageService.findAllLinksByChatId(chatId);

        List<LinkResponse> links = chats.stream()
                .map(it -> new LinkResponse(it.getId(), it.getUrl().toString()))
                .toList();

        if (links.isEmpty()) {
            throw new ClientException(HttpStatus.NO_CONTENT.value(), "No tracked links");
        }

        ListLinksResponse listLinksResponse = new ListLinksResponse(links, links.size());
        return ResponseEntity.status(HttpStatus.OK).body(listLinksResponse);
    }

    @PostMapping("/links")
    public ResponseEntity<Object> addLink(@RequestHeader("Tg-Chat-Id") Long chatId, @RequestBody AddLinkRequest request) {
        boolean isLinkAlreadyTracked = chatStorageService.findAllLinksByChatId(chatId)
                .stream().map(it -> it.getUrl().toString())
                .anyMatch(it -> Objects.equals(it, request.link));

        if (isLinkAlreadyTracked) {
            throw new ClientException(HttpStatus.BAD_REQUEST.value(), "No tracked links");
        }

        LinkDTO linkDTO = LinkDTO.builder()
                .url(URI.create(request.link))
                .build();
        linkDTO = linkStorageService.addLink(linkDTO);

        ChatDTO chatDTO = ChatDTO.builder()
                .chatId(chatId)
                .linkId(linkDTO.getId())
                .build();

        chatStorageService.addUser(chatDTO);

        LinkResponse linkResponse = new LinkResponse(chatId, linkDTO.getUrl().toString());
        return ResponseEntity.status(HttpStatus.OK).body(linkResponse);
    }

    @PostMapping("/links/delete")
    public ResponseEntity<Object> removeLink(@RequestHeader("Tg-Chat-Id") Long chatId, @RequestBody RemoveLinkRequest request) {
        List<LinkDTO> linkDTOS = chatStorageService.findAllLinksByChatId(chatId);

        LinkDTO tracked = null;
        for (LinkDTO it : linkDTOS) {
            if (!it.getUrl().toString().equals(request.link)) {
                continue;
            }
            tracked = LinkDTO.builder()
                    .id(it.getId())
                    .url(it.getUrl())
                    .build();
        }

        boolean isLinkTracked = Objects.nonNull(tracked);
        if (!isLinkTracked) {
            throw new ClientException(HttpStatus.BAD_REQUEST.value(), "Link is not found");
        }

        chatStorageService.removeByChatIdAndLinkId(chatId, tracked.getId());
        linkStorageService.removeLink(tracked);

        LinkResponse linkResponse = new LinkResponse(chatId, tracked.getUrl().toString());
        return ResponseEntity.status(HttpStatus.OK).body(linkResponse);
    }
}
