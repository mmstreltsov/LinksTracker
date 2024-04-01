package scrapper.controllers;

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
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        ApiErrorResponse bodyOfResponse = ApiErrorResponse.builder()
                .exceptionName("Server undefined exception")
                .exceptionMessage(ex.getMessage())
                .build();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(ClientException.class)
    protected ResponseEntity<Object> handleConflictClientException(ClientException ex, WebRequest request) {
        ApiErrorResponse bodyOfResponse = ApiErrorResponse.builder()
                .code(String.valueOf(ex.getCode()))
                .exceptionMessage(ex.getMessage())
                .build();

        HttpStatus status;
        try {
            status = HttpStatus.valueOf(ex.getCode());
        } catch (Throwable ignored) {
            status = HttpStatus.BAD_REQUEST;
        }

        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), status, request);
    }
}
