package bot.controller;


import bot.dto.LinkUpdateRequest;
import bot.service.HandleLinkUpdate;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BotController {

    private final HandleLinkUpdate handler;

    @PostMapping(value = "/updates")
    public ResponseEntity<Object> updates(@RequestBody LinkUpdateRequest linkUpdateRequest) {
        handler.handleLinkUpdate(linkUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
