package site.board.boardtraining.domain.comment.dto.business;

import site.board.boardtraining.domain.comment.entity.ArticleComment;
import site.board.boardtraining.domain.member.dto.business.MemberDto;

public record ArticleCommentDto(
        Long commentId,
        String content,
        String status,
        MemberDto member
) {
    public static ArticleCommentDto from(ArticleComment articleComment) {
        return new ArticleCommentDto(
                articleComment.getId(),
                articleComment.getContent(),
                articleComment.getStatus().name(),
                MemberDto.from(articleComment.getMember())
        );
    }
}