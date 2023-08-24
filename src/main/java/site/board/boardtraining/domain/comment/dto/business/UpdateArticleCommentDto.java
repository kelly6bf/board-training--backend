package site.board.boardtraining.domain.comment.dto.business;

public record UpdateArticleCommentDto(
        Long memberId,
        String content
) {
    public static UpdateArticleCommentDto of(
            Long memberId,
            String content
    ) {
        return new UpdateArticleCommentDto(
                memberId,
                content
        );
    }
}