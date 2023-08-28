package site.board.boardtraining.domain.article.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import site.board.boardtraining.global.exception.ErrorCode;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public enum ArticleErrorCode
        implements ErrorCode {

    ARTICLE_NOT_FOUND("ART-C-001", NOT_FOUND, "존재하지 않는 게시글입니다."),
    ARTICLE_LIKE_REACTION_NOT_FOUND("ART-C-002", NOT_FOUND, "게시글 좋아요 반응이 존재하지 않습니다."),
    ARTICLE_DISLIKE_REACTION_NOT_FOUND("ART-C-003", NOT_FOUND, "게시글 싫어요 반응이 존재하지 않습니다."),
    ARTICLE_LIKE_REACTION_ALREADY_EXIST("ART-C-004", CONFLICT, "게시글 좋아요 반응이 이미 존재합니다."),
    ARTICLE_DISLIKE_REACTION_ALREADY_EXIST("ART-C-005", CONFLICT, "게시글 싫어요 반응이 이미 존재합니다.");

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