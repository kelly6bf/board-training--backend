package site.board.boardtraining.domain.auth.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import site.board.boardtraining.global.exception.ErrorCode;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum AuthErrorCode
        implements ErrorCode {

    INVALID_CREDENTIALS("AT-C-0001", UNAUTHORIZED, "유효하지 않은 인증 정보입니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    AuthErrorCode(
            String code,
            HttpStatus httpStatus,
            String message
    ) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}