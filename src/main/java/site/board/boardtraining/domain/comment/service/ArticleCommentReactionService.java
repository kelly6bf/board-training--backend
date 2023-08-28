package site.board.boardtraining.domain.comment.service;

import site.board.boardtraining.domain.comment.entity.ArticleComment;

public interface ArticleCommentReactionService {
    int getArticleCommentLikeCount(ArticleComment articleComment);

    void addArticleCommentLike(Long articleCommentId, Long memberId);

    void deleteArticleCommentLike(Long articleCommentId, Long memberId);

    int getArticleCommentDislikeCount(ArticleComment articleComment);

    void addArticleCommentDislike(Long articleCommentId, Long memberId);

    void deleteArticleCommentDislike(Long articleCommentId, Long memberId);
}