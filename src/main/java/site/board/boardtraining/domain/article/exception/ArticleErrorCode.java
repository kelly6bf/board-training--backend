package site.board.boardtraining.domain.article.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import site.board.boardtraining.global.exception.ErrorCode;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
public enum ArticleErrorCode
        implements ErrorCode {

    ARTICLE_NOT_FOUND("ART-C-001", NOT_FOUND, "존재하지 않는 게시글입니다."),
    ARTICLE_REACTION_NOT_FOUND("ART-C-002", NOT_FOUND, "게시글 반응이 존재하지 않습니다."),
    ARTICLE_REACTION_ALREADY_EXIST("ART-C-003", CONFLICT, "게시글 반응이 이미 존재합니다."),
    UNAUTHORIZED_ARTICLE_PROCESS("ART-C-004", UNAUTHORIZED, "해당 게시글을 처리할 권한이 없습니다."),
    UNAUTHORIZED_ARTICLE_REACTION_PROCESS("ART-C-005", UNAUTHORIZED, "해당 게시글 반응을 처리할 권한이 없습니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    ArticleErrorCode(
            String code,
            HttpStatus httpStatus,
            String message
    ) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}