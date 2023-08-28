package site.board.boardtraining.domain.reaction.exception;

import site.board.boardtraining.global.exception.BusinessException;
import site.board.boardtraining.global.exception.ErrorCode;

public class ReactionBusinessException
        extends BusinessException {
    public ReactionBusinessException(ErrorCode errorCode) {
        super(errorCode);
    }
}