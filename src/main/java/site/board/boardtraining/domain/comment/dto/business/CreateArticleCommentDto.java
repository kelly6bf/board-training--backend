package site.board.boardtraining.domain.comment.dto.business;

import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.comment.constant.ArticleCommentType;
import site.board.boardtraining.domain.comment.entity.ArticleComment;
import site.board.boardtraining.domain.member.entity.Member;

import static site.board.boardtraining.domain.comment.constant.ArticleCommentType.*;

public record CreateArticleCommentDto(
        Long articleId,
        Long memberId,
        String content,
        ArticleCommentType commentType,
        Long parentCommentId
) {
    public static CreateArticleCommentDto of(
            Long articleId,
            Long memberId,
            String content
    ) {
        return new CreateArticleCommentDto(
                articleId,
                memberId,
                content,
                PARENT,
                null
        );
    }

    public static CreateArticleCommentDto of(
            Long memberId,
            String content,
            Long parentCommentId
    ) {
        return new CreateArticleCommentDto(
                null,
                memberId,
                content,
                CHILD,
                parentCommentId
        );
    }

    public ArticleComment toEntity(
            Article article,
            Member member
    ) {
        return ArticleComment.of(
                content,
                commentType,
                parentCommentId,
                article,
                member
        );
    }
}