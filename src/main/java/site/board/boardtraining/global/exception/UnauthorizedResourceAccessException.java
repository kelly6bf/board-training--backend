package site.board.boardtraining.global.exception;

import static site.board.boardtraining.global.exception.GlobalErrorCode.UNAUTHORIZED_RESOURCE_ACCESS;

public class UnauthorizedResourceAccessException
        extends RuntimeException {

    private final ErrorCode errorCode;

    public UnauthorizedResourceAccessException() {
        super(UNAUTHORIZED_RESOURCE_ACCESS.getMessage());
        this.errorCode = UNAUTHORIZED_RESOURCE_ACCESS;
    }

    public UnauthorizedResourceAccessException(
            Throwable cause
    ) {
        super(
                UNAUTHORIZED_RESOURCE_ACCESS.getMessage(),
                cause
        );
        this.errorCode = UNAUTHORIZED_RESOURCE_ACCESS;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}