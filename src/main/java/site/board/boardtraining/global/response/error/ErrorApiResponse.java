package site.board.boardtraining.global.response.error;

import lombok.Getter;
import site.board.boardtraining.global.exception.ErrorCode;
import site.board.boardtraining.global.util.GsonUtil;

import java.time.LocalDateTime;

@Getter
public class ErrorApiResponse {

    private final String errorCode;
    private final String message;
    private final LocalDateTime timestamp = LocalDateTime.now();

    private ErrorApiResponse(
            final String errorCode,
            final String message
    ) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public static ErrorApiResponse of(
            final ErrorCode errorCode
    ) {
        return new ErrorApiResponse(
                errorCode.getCode(),
                errorCode.getMessage()
        );
    }

    public static ErrorApiResponse of(
            final ErrorCode errorCode,
            final String message
    ) {
        return new ErrorApiResponse(
                errorCode.getCode(),
                message
        );
    }

    public String convertResponseToJson() {
        return new GsonUtil().toJson(this);
    }
}