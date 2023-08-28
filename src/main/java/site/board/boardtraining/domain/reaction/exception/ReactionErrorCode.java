package site.board.boardtraining.domain.reaction.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import site.board.boardtraining.global.exception.ErrorCode;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public enum ReactionErrorCode
        implements ErrorCode {

    LIKE_REACTION_NOT_FOUND("RE-C-001", NOT_FOUND, "좋아요 반응이 존재하지 않습니다."),

    DISLIKE_REACTION_NOT_FOUND("RE-C-002", NOT_FOUND, "싫어요 반응이 존재하지 않습니다."),
    LIKE_REACTION_ALREADY_EXIST("RE-C-003", CONFLICT, "좋아요 반응이 이미 존재합니다."),
    DISLIKE_REACTION_ALREADY_EXIST("RE-C-004", CONFLICT, "싫어요 반응이 이미 존재합니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    ReactionErrorCode(
            String code,
            HttpStatus httpStatus,
            String message
    ) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}