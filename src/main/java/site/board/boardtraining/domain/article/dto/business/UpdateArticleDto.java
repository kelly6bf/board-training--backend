package site.board.boardtraining.domain.article.dto.business;

import site.board.boardtraining.domain.article.constant.ArticleStatus;

public record UpdateArticleDto(
        Long articleId,
        String title,
        String content,
        String thumbnailImageUrl,
        ArticleStatus status,
        Long memberId
) {
    public static UpdateArticleDto of(
            Long articleId,
            String title,
            String content,
            String thumbnailImageUrl,
            ArticleStatus status,
            Long memberId
    ) {
        return new UpdateArticleDto(
                articleId,
                title,
                content,
                thumbnailImageUrl,
                status,
                memberId
        );
    }
}