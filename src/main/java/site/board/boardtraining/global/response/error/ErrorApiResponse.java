package site.board.boardtraining.global.response.error;

import lombok.Getter;
import site.board.boardtraining.global.exception.ErrorCode;

import java.time.LocalDateTime;

@Getter
public class ErrorApiResponse {

    private final String errorCode;
    private final String message;
    private final LocalDateTime timestamp = LocalDateTime.now();

    private ErrorApiResponse(
            final String message,
            final String errorCode
    ) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public static ErrorApiResponse of(
            final ErrorCode errorCode
    ) {
        return new ErrorApiResponse(
                errorCode.getCode(),
                errorCode.getMessage()
        );
    }
}