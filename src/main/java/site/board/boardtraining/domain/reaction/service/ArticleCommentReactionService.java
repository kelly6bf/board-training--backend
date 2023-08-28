package site.board.boardtraining.domain.reaction.service;

import site.board.boardtraining.domain.comment.entity.ArticleComment;

public interface ArticleCommentReactionService {
    int getArticleCommentLikeCount(ArticleComment articleComment);

    void addArticleCommentLike(ArticleComment articleComment);

    void deleteArticleCommentLike(ArticleComment articleComment);

    int getArticleCommentDislikeCount(ArticleComment articleComment);

    void addArticleCommentDislike(ArticleComment articleComment);

    void deleteArticleCommentDislike(ArticleComment articleComment);
}