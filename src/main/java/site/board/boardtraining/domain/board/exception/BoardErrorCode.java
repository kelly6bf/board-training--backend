package site.board.boardtraining.domain.board.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import site.board.boardtraining.global.exception.ErrorCode;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public enum BoardErrorCode
        implements ErrorCode {

    BOARD_NOT_FOUND("BD-C-001", NOT_FOUND, "존재하지 않는 게시판입니다."),
    BOARD_LIKE_REACTION_NOT_FOUND("BD-C-002", NOT_FOUND, "게시판 좋아요 반응이 존재하지 않습니다."),
    BOARD_DISLIKE_REACTION_NOT_FOUND("BD-C-003", NOT_FOUND, "게시판 싫어요 반응이 존재하지 않습니다."),
    BOARD_LIKE_REACTION_ALREADY_EXIST("BD-C-004", CONFLICT, "게시판 좋아요 반응이 이미 존재합니다."),
    BOARD_DISLIKE_REACTION_ALREADY_EXIST("BD-C-005", CONFLICT, "게시판 싫어요 반응이 이미 존재합니다.");

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