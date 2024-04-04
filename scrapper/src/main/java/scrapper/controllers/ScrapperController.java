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

import java.util.List;

@AllArgsConstructor
@RestController
public class ScrapperController {

    private LinkStorageService linkStorageService;
    private ChatStorageService chatStorageService;

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<Object> registerChat(@PathVariable("id") Long id) {
        ChatDTO chatDTO = new ChatDTO(id);
        chatStorageService.addChat(chatDTO);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<Object> deleteChat(@PathVariable("id") Long id) {
        ChatDTO chatDTO = new ChatDTO(id);
        chatStorageService.removeChat(chatDTO);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @GetMapping("/links")
    public ResponseEntity<Object> getLinks(@RequestHeader("Tg-Chat-Id") Long chatId) {
        List<LinkDTO> linkDTOS = chatStorageService.findAllLinksByChatId(chatId);

        List<LinkResponse> links = linkDTOS.stream()
                .map(it -> new LinkResponse(chatId, it.getUrl()))
                .toList();

        if (links.isEmpty()) {
            throw new ClientException(HttpStatus.NO_CONTENT.value(), "No tracked links");
        }

        ListLinksResponse listLinksResponse = new ListLinksResponse(links, links.size());
        return ResponseEntity.status(HttpStatus.OK).body(listLinksResponse);
    }

    @PostMapping("/links")
    public ResponseEntity<Object> addLink(@RequestHeader("Tg-Chat-Id") Long chatId, @RequestBody AddLinkRequest request) {
        ChatDTO chatDTO = new ChatDTO(chatId);

        LinkDTO linkDTO = LinkDTO.builder()
                .url(request.link)
                .chat(chatDTO)
                .build();
        linkStorageService.addLink(linkDTO);

        LinkResponse linkResponse = new LinkResponse(chatId, linkDTO.getUrl());
        return ResponseEntity.status(HttpStatus.OK).body(linkResponse);
    }

    @PostMapping("/links/delete")
    public ResponseEntity<Object> removeLink(@RequestHeader("Tg-Chat-Id") Long chatId, @RequestBody RemoveLinkRequest request) {
        ChatDTO chatDTO = new ChatDTO(chatId);

        LinkDTO linkDTO = LinkDTO.builder()
                .url(request.link)
                .chat(chatDTO)
                .build();
        linkStorageService.removeLink(linkDTO);

        LinkResponse linkResponse = new LinkResponse(chatId, linkDTO.getUrl());
        return ResponseEntity.status(HttpStatus.OK).body(linkResponse);
    }
}
