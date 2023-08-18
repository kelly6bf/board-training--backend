package site.board.boardtraining.domain.board.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import site.board.boardtraining.global.exception.ErrorCode;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public enum BoardErrorCode
        implements ErrorCode {

    BOARD_NOT_FOUND("BD-C-001", NOT_FOUND, "존재하지 않는 게시판입니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    BoardErrorCode(
            String code,
            HttpStatus httpStatus,
            String message
    ) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}