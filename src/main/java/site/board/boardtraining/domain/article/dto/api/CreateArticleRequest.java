package site.board.boardtraining.domain.article.dto.api;

import site.board.boardtraining.domain.article.constant.ArticleStatus;
import site.board.boardtraining.domain.article.dto.business.CreateArticleDto;

import java.util.Set;

public record CreateArticleRequest(
        String title,
        String content,
        String thumbnailImageUrl,
        Set<String> hashtags,
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