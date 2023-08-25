package site.board.boardtraining.domain.comment.dto.business;

public record UpdateArticleCommentDto(
        Long memberId,
        Long commentId,
        String content
) {
    public static UpdateArticleCommentDto of(
            Long memberId,
            Long commentId,
            String content
    ) {
        return new UpdateArticleCommentDto(
                memberId,
                commentId,
                content
        );
    }
}