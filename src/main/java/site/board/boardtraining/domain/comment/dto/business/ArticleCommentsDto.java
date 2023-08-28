package site.board.boardtraining.domain.comment.dto.business;

import site.board.boardtraining.domain.comment.entity.ArticleComment;
import site.board.boardtraining.domain.member.dto.business.MemberDto;

import java.util.List;

public record ArticleCommentsDto(
        Long commentId,
        String content,
        Integer likeCount,
        Integer dislikeCount,
        String status,
        MemberDto member,
        List<ArticleCommentDto> childComments
) {
    public static ArticleCommentsDto from(
            ArticleComment articleComment,
            List<ArticleCommentDto> childComments,
            Integer likeCount,
            Integer dislikeCount
    ) {
        return new ArticleCommentsDto(
                articleComment.getId(),
                articleComment.getContent(),
                likeCount,
                dislikeCount,
                articleComment.getStatus().name(),
                MemberDto.from(articleComment.getMember()),
                childComments
        );
    }
}