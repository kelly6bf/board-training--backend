package site.board.boardtraining.domain.article.dto.api;

import site.board.boardtraining.domain.article.constant.ArticleStatus;
import site.board.boardtraining.domain.article.dto.business.CreateArticleDto;

public record CreateArticleRequest(
        String title,
        String content,
        String thumbnailImageUrl,
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
                status,
                boardId,
                memberId
        );
    }
}