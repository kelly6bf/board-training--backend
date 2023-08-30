package site.board.boardtraining.domain.board.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import site.board.boardtraining.global.exception.ErrorCode;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum BoardErrorCode
        implements ErrorCode {

    BOARD_NOT_FOUND("BD-C-001", NOT_FOUND, "존재하지 않는 게시판입니다."),
    BOARD_REACTION_NOT_FOUND("BD-C-002", NOT_FOUND, "게시판 반응이 존재하지 않습니다."),
    BOARD_REACTION_ALREADY_EXIST("BD-C-003", CONFLICT, "게시판 반응이 이미 존재합니다."),
    UNAUTHORIZED_BOARD_PROCESS("BD-C-004", UNAUTHORIZED, "게시판을 처리할 권한이 없습니다."),
    UNAUTHORIZED_BOARD_REACTION_PROCESS("BD-C-005", UNAUTHORIZED, "게시판 반응을 처리할 권한이 없습니다.");

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