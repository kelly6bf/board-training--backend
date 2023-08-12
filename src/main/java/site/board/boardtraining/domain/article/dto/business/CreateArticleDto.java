package site.board.boardtraining.domain.article.dto.business;

import site.board.boardtraining.domain.article.constant.ArticleStatus;

public record CreateArticleDto(
        String title,
        String content,
        ArticleStatus status,
        String memberPersonalId
) {
    public static CreateArticleDto of(
            String title,
            String content,
            ArticleStatus status,
            String memberPersonalId
    ) {
        return new CreateArticleDto(
                title,
                content,
                status,
                memberPersonalId
        );
    }
}