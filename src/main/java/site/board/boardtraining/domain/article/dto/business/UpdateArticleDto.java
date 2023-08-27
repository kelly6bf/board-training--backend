package site.board.boardtraining.domain.article.dto.business;

import site.board.boardtraining.domain.article.constant.ArticleStatus;

import java.util.Set;

public record UpdateArticleDto(
        Long articleId,
        String title,
        String content,
        String thumbnailImageUrl,
        Set<String> hashtags,
        ArticleStatus status,
        Long memberId
) {
    public static UpdateArticleDto of(
            Long articleId,
            String title,
            String content,
            String thumbnailImageUrl,
            Set<String> hashtags,
            ArticleStatus status,
            Long memberId
    ) {
        return new UpdateArticleDto(
                articleId,
                title,
                content,
                thumbnailImageUrl,
                hashtags,
                status,
                memberId
        );
    }
}