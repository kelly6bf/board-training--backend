package site.board.boardtraining.domain.article.dto.business;

import site.board.boardtraining.domain.article.constant.ArticleStatus;

public record UpdateArticleDto(
        Long articleId,
        String title,
        String content,
        ArticleStatus status,
        String memberPersonalId
) {
    public static UpdateArticleDto of(
            Long articleId,
            String title,
            String content,
            ArticleStatus status,
            String memberPersonalId
    ) {
        return new UpdateArticleDto(
                articleId,
                title,
                content,
                status,
                memberPersonalId
        );
    }
}