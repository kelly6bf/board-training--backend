package site.board.boardtraining.domain.article.service;

import site.board.boardtraining.domain.article.entity.Article;

public interface ArticleReactionService {
    int getArticleLikeCount(Article article);

    void addArticleLike(Long articleId, Long memberId);

    void deleteArticleLike(Long articleId, Long memberId);

    int getArticleDislikeCount(Article article);

    void addArticleDislike(Long articleId, Long memberId);

    void deleteArticleDislike(Long articleId, Long memberId);
}