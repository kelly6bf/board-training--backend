package site.board.boardtraining.domain.article.dto.api;

import site.board.boardtraining.domain.article.constant.ArticleStatus;
import site.board.boardtraining.domain.article.dto.business.UpdateArticleDto;

import java.util.LinkedHashSet;

public record UpdateArticleRequest(
        String title,
        String content,
        String thumbnailImageUrl,
        LinkedHashSet<String> hashtags,
        ArticleStatus status
) {
    public UpdateArticleDto toDto(
            Long articleId,
            Long memberId
    ) {
        return UpdateArticleDto.of(
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