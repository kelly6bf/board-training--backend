package site.board.boardtraining.domain.article.dto.api;

import site.board.boardtraining.domain.article.constant.ArticleStatus;
import site.board.boardtraining.domain.article.dto.business.CreateArticleDto;

public record CreateArticleRequest(
        String title,
        String content,
        ArticleStatus status
) {
    public CreateArticleDto toDto(
            Long boardId,
            Long memberId
    ) {
        return CreateArticleDto.of(
                title,
                content,
                status,
                boardId,
                memberId
        );
    }
}