package site.board.boardtraining.global.exception;

import lombok.Getter;

@Getter
public class UnauthorizedResourceProcessException
        extends RuntimeException {

    private final ErrorCode errorCode;

    public UnauthorizedResourceProcessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public UnauthorizedResourceProcessException(
            Throwable cause,
            ErrorCode errorCode
    ) {
        super(
                errorCode.getMessage(),
                cause
        );
        this.errorCode = errorCode;
    }
}