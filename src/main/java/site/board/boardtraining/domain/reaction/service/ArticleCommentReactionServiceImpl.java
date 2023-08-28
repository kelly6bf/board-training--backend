package site.board.boardtraining.domain.reaction.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.board.boardtraining.domain.comment.entity.ArticleComment;
import site.board.boardtraining.domain.reaction.constant.ReactionType;
import site.board.boardtraining.domain.reaction.entity.ArticleCommentReaction;
import site.board.boardtraining.domain.reaction.exception.ReactionBusinessException;
import site.board.boardtraining.domain.reaction.repository.ArticleCommentReactionRepository;

import static site.board.boardtraining.domain.reaction.constant.ReactionType.DISLIKE;
import static site.board.boardtraining.domain.reaction.constant.ReactionType.LIKE;
import static site.board.boardtraining.domain.reaction.exception.ReactionErrorCode.*;

@Transactional
@Service
public class ArticleCommentReactionServiceImpl
        implements ArticleCommentReactionService {

    private final ArticleCommentReactionRepository articleCommentReactionRepository;

    public ArticleCommentReactionServiceImpl(ArticleCommentReactionRepository articleCommentReactionRepository) {
        this.articleCommentReactionRepository = articleCommentReactionRepository;
    }

    @Override
    public int getArticleCommentLikeCount(ArticleComment articleComment) {
        return articleCommentReactionRepository.countAllByTypeAndArticleComment(
                LIKE,
                articleComment
        );
    }

    @Override
    public void addArticleCommentLike(ArticleComment articleComment) {
        if (checkReactionExistence(LIKE, articleComment))
            throw new ReactionBusinessException(LIKE_REACTION_ALREADY_EXIST);

        articleCommentReactionRepository.save(
                ArticleCommentReaction.of(
                        LIKE,
                        articleComment,
                        articleComment.getMember()
                )
        );
    }

    @Override
    public void deleteArticleCommentLike(ArticleComment articleComment) {
        if (!checkReactionExistence(LIKE, articleComment))
            throw new ReactionBusinessException(LIKE_REACTION_NOT_FOUND);

        articleCommentReactionRepository.deleteAllByArticleCommentAndMember(
                articleComment,
                articleComment.getMember()
        );
    }

    @Override
    public int getArticleCommentDislikeCount(ArticleComment articleComment) {
        return articleCommentReactionRepository.countAllByTypeAndArticleComment(
                DISLIKE,
                articleComment
        );
    }

    @Override
    public void addArticleCommentDislike(ArticleComment articleComment) {

        if (checkReactionExistence(DISLIKE, articleComment))
            throw new ReactionBusinessException(DISLIKE_REACTION_ALREADY_EXIST);

        articleCommentReactionRepository.save(
                ArticleCommentReaction.of(
                        DISLIKE,
                        articleComment,
                        articleComment.getMember()
                )
        );
    }

    @Override
    public void deleteArticleCommentDislike(ArticleComment articleComment) {

        if (!checkReactionExistence(DISLIKE, articleComment))
            throw new ReactionBusinessException(DISLIKE_REACTION_NOT_FOUND);

        articleCommentReactionRepository.save(
                ArticleCommentReaction.of(
                        DISLIKE,
                        articleComment,
                        articleComment.getMember()
                )
        );
    }

    private boolean checkReactionExistence(
            ReactionType reactionType,
            ArticleComment articleComment
    ) {
        return articleCommentReactionRepository.existsByTypeAndArticleCommentAndMember(
                reactionType,
                articleComment,
                articleComment.getMember()
        );
    }
}