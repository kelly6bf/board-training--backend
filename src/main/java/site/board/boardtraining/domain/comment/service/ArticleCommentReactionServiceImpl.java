package site.board.boardtraining.domain.comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.board.boardtraining.domain.comment.entity.ArticleComment;
import site.board.boardtraining.domain.comment.constant.ArticleCommentReactionType;
import site.board.boardtraining.domain.comment.entity.ArticleCommentReaction;
import site.board.boardtraining.domain.comment.exception.ArticleCommentBusinessException;
import site.board.boardtraining.domain.comment.repository.ArticleCommentReactionRepository;

import static site.board.boardtraining.domain.comment.constant.ArticleCommentReactionType.DISLIKE;
import static site.board.boardtraining.domain.comment.constant.ArticleCommentReactionType.LIKE;
import static site.board.boardtraining.domain.comment.exception.ArticleCommentErrorCode.*;

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
            throw new ArticleCommentBusinessException(ARTICLE_COMMENT_LIKE_REACTION_ALREADY_EXIST);

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
            throw new ArticleCommentBusinessException(ARTICLE_COMMENT_LIKE_REACTION_NOT_FOUND);

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
            throw new ArticleCommentBusinessException(ARTICLE_COMMENT_DISLIKE_REACTION_ALREADY_EXIST);

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
            throw new ArticleCommentBusinessException(ARTICLE_COMMENT_DISLIKE_REACTION_NOT_FOUND);

        articleCommentReactionRepository.save(
                ArticleCommentReaction.of(
                        DISLIKE,
                        articleComment,
                        articleComment.getMember()
                )
        );
    }

    private boolean checkReactionExistence(
            ArticleCommentReactionType articleCommentReactionType,
            ArticleComment articleComment
    ) {
        return articleCommentReactionRepository.existsByTypeAndArticleCommentAndMember(
                articleCommentReactionType,
                articleComment,
                articleComment.getMember()
        );
    }
}