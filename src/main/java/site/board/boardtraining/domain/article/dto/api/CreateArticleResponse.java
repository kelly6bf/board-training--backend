package site.board.boardtraining.domain.article.dto.api;

public record CreateArticleResponse(
        Long articleId
) {
    public static CreateArticleResponse of(
            Long articleId
    ) {
        return new CreateArticleResponse(articleId);
    }
}