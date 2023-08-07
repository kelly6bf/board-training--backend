package site.board.boardtraining.global.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.board.boardtraining.global.response.error.ErrorApiResponse;

import static site.board.boardtraining.global.exception.GlobalErrorCode.RESOURCE_NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<ErrorApiResponse> handleEntityNotFoundException(
            EntityNotFoundException e
    ) {
        log.error("[handle EntityNotFoundException] - {}", e.getMessage());

        return new ResponseEntity<>(
                ErrorApiResponse.of(
                        RESOURCE_NOT_FOUND,
                        e.getMessage()
                ),
                RESOURCE_NOT_FOUND.getHttpStatus()
        );
    }
}