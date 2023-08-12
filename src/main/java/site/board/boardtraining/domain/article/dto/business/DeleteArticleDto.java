package site.board.boardtraining.domain.article.dto.business;

public record DeleteArticleDto(
        Long articleId,
        String memberPersonalId
) {
    public static DeleteArticleDto of(
            Long articleId,
            String memberPersonalId
    ) {
        return new DeleteArticleDto(
                articleId,
                memberPersonalId
        );
    }
}