package site.board.boardtraining.domain.reaction.service;

import site.board.boardtraining.domain.article.entity.Article;

public interface ArticleReactionService {
    int getArticleLikeCount(Article article);

    void addArticleLike(Article article);

    void deleteArticleLike(Article article);

    int getArticleDislikeCount(Article article);

    void addArticleDislike(Article article);

    void deleteArticleDislike(Article article);
}