package scrapper.controllers.errors;

import lombok.Getter;

@Getter
public class ClientException extends RuntimeException {

    private final Integer code;

    public ClientException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}


