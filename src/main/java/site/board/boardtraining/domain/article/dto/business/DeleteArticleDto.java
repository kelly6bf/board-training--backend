package site.board.boardtraining.domain.article.dto.business;

public record DeleteArticleDto(
        Long articleId,
        Long memberId
) {
    public static DeleteArticleDto of(
            Long articleId,
            Long memberId
    ) {
        return new DeleteArticleDto(
                articleId,
                memberId
        );
    }
}