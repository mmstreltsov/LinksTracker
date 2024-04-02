package scrapper.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import scrapper.controllers.dto.ApiErrorResponse;
import scrapper.controllers.errors.ClientException;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        ApiErrorResponse bodyOfResponse = ApiErrorResponse.builder()
                .description("Server undefined exception: " + ex.getMessage())
                .build();

        log.info("Server error: " + ex.getMessage());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE, request);
    }

    @ExceptionHandler(ClientException.class)
    protected ResponseEntity<Object> handleConflictClientException(ClientException ex, WebRequest request) {
        ApiErrorResponse bodyOfResponse = ApiErrorResponse.builder()
                .code(String.valueOf(ex.getCode()))
                .description(ex.getMessage())
                .build();

        HttpStatus status;
        try {
            status = HttpStatus.valueOf(ex.getCode());
        } catch (Throwable ignored) {
            status = HttpStatus.BAD_REQUEST;
        }

        log.info("Client error: " + ex.getMessage());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), status, request);
    }
}
