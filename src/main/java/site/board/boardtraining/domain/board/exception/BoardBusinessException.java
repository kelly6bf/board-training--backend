package site.board.boardtraining.domain.board.exception;

import site.board.boardtraining.global.exception.BusinessException;
import site.board.boardtraining.global.exception.ErrorCode;

public class BoardBusinessException
        extends BusinessException {

    public BoardBusinessException(ErrorCode errorCode) {
        super(errorCode);
    }
}