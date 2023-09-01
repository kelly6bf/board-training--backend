package site.board.boardtraining.domain.article.service;

public interface ArticleReactionService {
    void addArticleLike(Long articleId, Long memberId);

    void deleteArticleLike(Long articleId, Long memberId);

    void addArticleDislike(Long articleId, Long memberId);

    void deleteArticleDislike(Long articleId, Long memberId);
}