package scrapper.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import scrapper.dto.AddLinkRequest;
import scrapper.dto.ApiErrorResponse;
import scrapper.dto.LinkResponse;
import scrapper.dto.ListLinksResponse;
import scrapper.dto.RemoveLinkRequest;

@RestController
public class ScrapperController {

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<Object> registerChat(@PathVariable("id") Long id) {
        if (false) {
            ApiErrorResponse response = ApiErrorResponse.builder()
                    .description("Некорректные параметры запроса")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<Object> deleteChat(@PathVariable("id") Long id) {
        if (false) {
            ApiErrorResponse response = ApiErrorResponse.builder()
                    .description("Некорректные параметры запроса")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (false) {
            ApiErrorResponse response = ApiErrorResponse.builder()
                    .description("Чат не существует")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @GetMapping("/links")
    public ResponseEntity<Object> getLinks(@RequestHeader("Tg-Chat-Id") Long chatId) {
        if (false) {
            ApiErrorResponse response = ApiErrorResponse.builder()
                    .description("Некорректные параметры запроса")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        ListLinksResponse listLinksResponse = new ListLinksResponse();
        return ResponseEntity.status(HttpStatus.OK).body(listLinksResponse);
    }

    @PostMapping("/links")
    public ResponseEntity<Object> addLink(@RequestHeader("Tg-Chat-Id") Long chatId, @RequestBody AddLinkRequest request) {
        if (false) {
            ApiErrorResponse response = ApiErrorResponse.builder()
                    .description("Некорректные параметры запроса")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        LinkResponse linkResponse = new LinkResponse();
        return ResponseEntity.status(HttpStatus.OK).body(linkResponse);
    }

    @DeleteMapping("/links")
    public ResponseEntity<Object> removeLink(@RequestHeader("Tg-Chat-Id") Long chatId, @RequestBody RemoveLinkRequest request) {
        if (false) {
            ApiErrorResponse response = ApiErrorResponse.builder()
                    .description("Некорректные параметры запроса")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (false) {
            ApiErrorResponse response = ApiErrorResponse.builder()
                    .description("Ссылка не найдена")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        LinkResponse linkResponse = new LinkResponse();
        return ResponseEntity.status(HttpStatus.OK).body(linkResponse);
    }
}
