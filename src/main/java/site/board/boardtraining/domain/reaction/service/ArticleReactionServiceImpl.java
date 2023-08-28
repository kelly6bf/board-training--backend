package site.board.boardtraining.domain.reaction.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.reaction.constant.ReactionType;
import site.board.boardtraining.domain.reaction.entity.ArticleReaction;
import site.board.boardtraining.domain.reaction.exception.ReactionBusinessException;
import site.board.boardtraining.domain.reaction.repository.ArticleReactionRepository;

import static site.board.boardtraining.domain.reaction.constant.ReactionType.DISLIKE;
import static site.board.boardtraining.domain.reaction.constant.ReactionType.LIKE;
import static site.board.boardtraining.domain.reaction.exception.ReactionErrorCode.*;

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
            throw new ReactionBusinessException(LIKE_REACTION_ALREADY_EXIST);

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
            throw new ReactionBusinessException(LIKE_REACTION_NOT_FOUND);

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
            throw new ReactionBusinessException(DISLIKE_REACTION_ALREADY_EXIST);

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
            throw new ReactionBusinessException(DISLIKE_REACTION_NOT_FOUND);

        articleReactionRepository.deleteAllByArticleAndMember(
                article,
                article.getMember()
        );
    }

    private boolean checkReactionExistence(
            ReactionType reactionType,
            Article article
    ) {
        return articleReactionRepository.existsByTypeAndArticleAndMember(
                reactionType,
                article,
                article.getMember()
        );
    }
}