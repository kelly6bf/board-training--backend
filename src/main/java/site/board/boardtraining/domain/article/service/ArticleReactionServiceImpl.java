package site.board.boardtraining.domain.article.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.board.boardtraining.domain.article.constant.ArticleReactionType;
import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.article.entity.ArticleReaction;
import site.board.boardtraining.domain.article.exception.ArticleBusinessException;
import site.board.boardtraining.domain.article.repository.ArticleReactionRepository;

import static site.board.boardtraining.domain.article.constant.ArticleReactionType.DISLIKE;
import static site.board.boardtraining.domain.article.constant.ArticleReactionType.LIKE;
import static site.board.boardtraining.domain.article.exception.ArticleErrorCode.*;

@Transactional
@Service
public class ArticleReactionServiceImpl
        implements ArticleReactionService {

    private final ArticleReactionRepository articleReactionRepository;

    public ArticleReactionServiceImpl(ArticleReactionRepository articleReactionRepository) {
        this.articleReactionRepository = articleReactionRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public int getArticleLikeCount(Article article) {
        return articleReactionRepository.countAllByTypeAndArticle(
                LIKE,
                article
        );
    }

    @Override
    public void addArticleLike(Article article) {

        if (checkReactionExistence(LIKE, article))
            throw new ArticleBusinessException(ARTICLE_LIKE_REACTION_ALREADY_EXIST);

        articleReactionRepository.save(
                ArticleReaction.of(
                        LIKE,
                        article,
                        article.getMember()
                )
        );
    }

    @Override
    public void deleteArticleLike(Article article) {

        if (!checkReactionExistence(LIKE, article))
            throw new ArticleBusinessException(ARTICLE_LIKE_REACTION_NOT_FOUND);

        articleReactionRepository.deleteAllByArticleAndMember(
                article,
                article.getMember()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public int getArticleDislikeCount(Article article) {
        return articleReactionRepository.countAllByTypeAndArticle(
                DISLIKE,
                article
        );
    }

    @Override
    public void addArticleDislike(Article article) {

        if (checkReactionExistence(DISLIKE, article))
            throw new ArticleBusinessException(ARTICLE_DISLIKE_REACTION_ALREADY_EXIST);

        articleReactionRepository.save(
                ArticleReaction.of(
                        DISLIKE,
                        article,
                        article.getMember()
                )
        );
    }

    @Override
    public void deleteArticleDislike(Article article) {

        if (!checkReactionExistence(DISLIKE, article))
            throw new ArticleBusinessException(ARTICLE_DISLIKE_REACTION_NOT_FOUND);

        articleReactionRepository.deleteAllByArticleAndMember(
                article,
                article.getMember()
        );
    }

    private boolean checkReactionExistence(
            ArticleReactionType reactionType,
            Article article
    ) {
        return articleReactionRepository.existsByTypeAndArticleAndMember(
                reactionType,
                article,
                article.getMember()
        );
    }
}