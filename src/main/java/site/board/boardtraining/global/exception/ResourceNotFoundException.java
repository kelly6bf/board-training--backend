package site.board.boardtraining.global.exception;

public abstract class ResourceNotFoundException
        extends RuntimeException {
    private final ErrorCode errorCode;

    protected ResourceNotFoundException(
            final ErrorCode errorCode
    ) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    protected ResourceNotFoundException(
            final ErrorCode errorCode,
            Throwable cause
    ) {
        super(
                errorCode.getMessage(),
                cause
        );
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}