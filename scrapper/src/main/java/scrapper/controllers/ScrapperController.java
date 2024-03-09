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
import scrapper.controllers.dto.ApiErrorResponse;
import scrapper.controllers.dto.LinkResponse;
import scrapper.controllers.dto.ListLinksResponse;
import scrapper.controllers.dto.RemoveLinkRequest;
import scrapper.model.LinkStorageService;
import scrapper.model.entity.Link;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@RestController
public class ScrapperController {

    private LinkStorageService linkStorageService;

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<Object> registerChat(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<Object> deleteChat(@PathVariable("id") Long id) {
        linkStorageService.removeUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @GetMapping("/links")
    public ResponseEntity<Object> getLinks(@RequestHeader("Tg-Chat-Id") Long chatId) {
        List<Link> chats = linkStorageService.findAllLinksByChatId(chatId);

        List<LinkResponse> links = chats.stream()
                .map(it -> new LinkResponse(it.getId(), it.getUrl().toString()))
                .toList();

        if (links.isEmpty()) {
            ApiErrorResponse response = ApiErrorResponse.builder()
                    .description("No tracked links")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        ListLinksResponse listLinksResponse = new ListLinksResponse(links, links.size());
        return ResponseEntity.status(HttpStatus.OK).body(listLinksResponse);
    }

    @PostMapping("/links")
    public ResponseEntity<Object> addLink(@RequestHeader("Tg-Chat-Id") Long chatId, @RequestBody AddLinkRequest request) {
        boolean isLinkAlreadyTracked = linkStorageService.findAllLinksByChatId(chatId)
                .stream().map(it -> it.getUrl().toString())
                .anyMatch(it -> Objects.equals(it, request.link));

        if (isLinkAlreadyTracked) {
            ApiErrorResponse response = ApiErrorResponse.builder()
                    .description("Некорректная ссылка: уже существует")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Link link = Link.builder()
                .url(URI.create(request.link))
                .build();

        link = linkStorageService.addLink(link);

        LinkResponse linkResponse = new LinkResponse(chatId, link.getUrl().toString());
        return ResponseEntity.status(HttpStatus.OK).body(linkResponse);
    }

    @PostMapping("/links/delete")
    public ResponseEntity<Object> removeLink(@RequestHeader("Tg-Chat-Id") Long chatId, @RequestBody RemoveLinkRequest request) {
        boolean isLinkTracked = linkStorageService.findAllLinksByChatId(chatId)
                .stream().map(it -> it.getUrl().toString())
                .anyMatch(it -> Objects.equals(it, request.link));

        if (!isLinkTracked) {
            ApiErrorResponse response = ApiErrorResponse.builder()
                    .description("Ссылка не найдена")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Link link = Link.builder()
                .url(URI.create(request.link))
                .build();

        linkStorageService.removeLink(link);

        LinkResponse linkResponse = new LinkResponse(chatId, link.getUrl().toString());
        return ResponseEntity.status(HttpStatus.OK).body(linkResponse);
    }
}
