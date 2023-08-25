package site.board.boardtraining.domain.comment.dto.api;

import site.board.boardtraining.domain.comment.dto.business.CreateArticleCommentDto;

public record CreateArticleCommentRequest(
        String content
) {
    public CreateArticleCommentDto toCreateParentCommentDto(
            Long articleId,
            Long memberId

    ) {
        return CreateArticleCommentDto.of(
                articleId,
                memberId,
                content
        );
    }

    public CreateArticleCommentDto toCreateChildCommentDto(
            Long memberId,
            Long parentCommentId
    ) {
        return CreateArticleCommentDto.of(
                memberId,
                content,
                parentCommentId
        );
    }
}