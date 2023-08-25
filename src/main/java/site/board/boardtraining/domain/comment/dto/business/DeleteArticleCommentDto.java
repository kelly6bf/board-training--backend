package site.board.boardtraining.domain.comment.dto.business;

public record DeleteArticleCommentDto(
        Long memberId,
        Long commentId
) {
    public static DeleteArticleCommentDto of(
            Long memberId,
            Long commentId
    ) {
        return new DeleteArticleCommentDto(
                memberId,
                commentId
        );
    }
}