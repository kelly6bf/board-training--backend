package site.board.boardtraining.domain.comment.dto.api;

import site.board.boardtraining.domain.comment.dto.business.UpdateArticleCommentDto;

public record UpdateArticleCommentRequest(
        String content
) {
    public UpdateArticleCommentDto toDto(
            Long memberId,
            Long commentId
    ) {
        return UpdateArticleCommentDto.of(
                memberId,
                commentId,
                content
        );
    }
}