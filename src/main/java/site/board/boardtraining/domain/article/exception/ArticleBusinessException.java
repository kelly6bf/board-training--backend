package site.board.boardtraining.domain.article.exception;

import site.board.boardtraining.global.exception.BusinessException;
import site.board.boardtraining.global.exception.ErrorCode;

public class ArticleBusinessException
        extends BusinessException {
    public ArticleBusinessException(ErrorCode errorCode) {
        super(errorCode);
    }
}