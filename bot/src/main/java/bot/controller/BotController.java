package bot.controller;


import bot.dto.ApiErrorResponse;
import bot.dto.LinkUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotController {

    @PostMapping(value = "/updates")
    public ResponseEntity<Object> updates(@RequestBody LinkUpdateRequest linkUpdateRequest) {
        if (false) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrorResponse());
        }
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
