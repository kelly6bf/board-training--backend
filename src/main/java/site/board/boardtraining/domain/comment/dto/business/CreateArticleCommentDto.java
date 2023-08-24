package site.board.boardtraining.domain.comment.dto.business;

public record CreateArticleCommentDto(
        Long articleId,
        Long memberId,
        String content,
        Long parentCommentId
) {
    public static CreateArticleCommentDto of(
            Long articleId,
            Long memberId,
            String content
    ) {
        return new CreateArticleCommentDto(
                articleId,
                memberId,
                content,
                null
        );
    }

    public static CreateArticleCommentDto of(
            Long memberId,
            String content,
            Long parentCommentId
    ) {
        return new CreateArticleCommentDto(
                null,
                memberId,
                content,
                parentCommentId
        );
    }
}