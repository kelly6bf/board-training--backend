package site.board.boardtraining.domain.comment.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import site.board.boardtraining.global.exception.ErrorCode;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ArticleCommentErrorCode
        implements ErrorCode {

    ARTICLE_COMMENT_NOT_FOUND("RE-C-001", NOT_FOUND, "게시글 댓글이 존재하지 않습니다."),
    ARTICLE_COMMENT_REACTION_NOT_FOUND("RE-C-002", NOT_FOUND, "게시글 댓글 반응이 존재하지 않습니다."),
    ARTICLE_COMMENT_REACTION_ALREADY_EXIST("RE-C-003", CONFLICT, "게시글 댓글 반응이 이미 존재합니다."),
    UNAUTHORIZED_ARTICLE_COMMENT_PROCESS("RE-C-004", UNAUTHORIZED, "게시글 댓글을 처리할 권한이 없습니다."),
    UNAUTHORIZED_ARTICLE_COMMENT_REACTION_PROCESS("RE-C-005", UNAUTHORIZED, "게시글 댓글 반응을 처리할 권한이 없습니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    ArticleCommentErrorCode(
            String code,
            HttpStatus httpStatus,
            String message
    ) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}