package site.board.boardtraining.domain.comment.exception;

import site.board.boardtraining.global.exception.BusinessException;
import site.board.boardtraining.global.exception.ErrorCode;

public class ArticleCommentBusinessException
        extends BusinessException {
    public ArticleCommentBusinessException(ErrorCode errorCode) {
        super(errorCode);
    }
}