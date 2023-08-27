package site.board.boardtraining.domain.article.dto.api;

import site.board.boardtraining.domain.article.constant.ArticleStatus;
import site.board.boardtraining.domain.article.dto.business.CreateArticleDto;

import java.util.LinkedHashSet;

public record CreateArticleRequest(
        String title,
        String content,
        String thumbnailImageUrl,
        LinkedHashSet<String> hashtags,
        ArticleStatus status
) {
    public CreateArticleDto toDto(
            Long boardId,
            Long memberId
    ) {
        return CreateArticleDto.of(
                title,
                content,
                thumbnailImageUrl,
                hashtags,
                status,
                boardId,
                memberId
        );
    }
}