package site.board.boardtraining.domain.comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.board.boardtraining.domain.comment.entity.ArticleComment;
import site.board.boardtraining.domain.comment.entity.ArticleCommentReaction;
import site.board.boardtraining.domain.comment.exception.ArticleCommentBusinessException;
import site.board.boardtraining.domain.comment.repository.ArticleCommentReactionRepository;
import site.board.boardtraining.domain.comment.repository.ArticleCommentRepository;
import site.board.boardtraining.domain.member.entity.Member;
import site.board.boardtraining.domain.member.repository.MemberRepository;
import site.board.boardtraining.global.exception.ResourceNotFoundException;
import site.board.boardtraining.global.exception.UnauthorizedResourceAccessException;

import java.util.Objects;

import static site.board.boardtraining.domain.comment.constant.ArticleCommentReactionType.DISLIKE;
import static site.board.boardtraining.domain.comment.constant.ArticleCommentReactionType.LIKE;
import static site.board.boardtraining.domain.comment.exception.ArticleCommentErrorCode.*;
import static site.board.boardtraining.domain.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;

@Transactional
@Service
public class ArticleCommentReactionServiceImpl
        implements ArticleCommentReactionService {

    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleCommentReactionRepository articleCommentReactionRepository;
    private final MemberRepository memberRepository;

    public ArticleCommentReactionServiceImpl(
            ArticleCommentRepository articleCommentRepository,
            ArticleCommentReactionRepository articleCommentReactionRepository,
            MemberRepository memberRepository
    ) {
        this.articleCommentRepository = articleCommentRepository;
        this.articleCommentReactionRepository = articleCommentReactionRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public int getArticleCommentLikeCount(ArticleComment articleComment) {
        return articleCommentReactionRepository.countAllByTypeAndArticleComment(
                LIKE,
                articleComment
        );
    }

    @Override
    public void addArticleCommentLike(Long articleCommentId, Long memberId) {

        ArticleComment articleComment = getArticleCommentEntity(articleCommentId);
        Member member = getMemberEntity(memberId);

        if (checkReactionExistence(articleComment, member))
            throw new ArticleCommentBusinessException(ARTICLE_COMMENT_REACTION_ALREADY_EXIST);

        articleCommentReactionRepository.save(
                ArticleCommentReaction.of(
                        LIKE,
                        articleComment,
                        getMemberEntity(memberId)
                )
        );
    }

    @Override
    public void deleteArticleCommentLike(Long articleCommentId, Long memberId) {

        ArticleComment articleComment = getArticleCommentEntity(articleCommentId);
        Member member = getMemberEntity(memberId);
        ArticleCommentReaction articleCommentReaction = getArticleCommentReactionEntity(articleComment, member);

        verifyReactionOwner(articleCommentReaction, member);

        articleCommentReactionRepository.delete(articleCommentReaction);
    }

    @Override
    public int getArticleCommentDislikeCount(ArticleComment articleComment) {
        return articleCommentReactionRepository.countAllByTypeAndArticleComment(
                DISLIKE,
                articleComment
        );
    }

    @Override
    public void addArticleCommentDislike(Long articleCommentId, Long memberId) {

        ArticleComment articleComment = getArticleCommentEntity(articleCommentId);
        Member member = getMemberEntity(memberId);

        if (checkReactionExistence(articleComment, member))
            throw new ArticleCommentBusinessException(ARTICLE_COMMENT_REACTION_ALREADY_EXIST);

        articleCommentReactionRepository.save(
                ArticleCommentReaction.of(
                        DISLIKE,
                        articleComment,
                        getMemberEntity(memberId)
                )
        );
    }

    @Override
    public void deleteArticleCommentDislike(Long articleCommentId, Long memberId) {

        ArticleComment articleComment = getArticleCommentEntity(articleCommentId);
        Member member = getMemberEntity(memberId);
        ArticleCommentReaction articleCommentReaction = getArticleCommentReactionEntity(articleComment, member);

        articleCommentReactionRepository.delete(articleCommentReaction);
    }

    private Member getMemberEntity(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException(MEMBER_NOT_FOUND));
    }

    private ArticleComment getArticleCommentEntity(Long articleCommentId) {
        return articleCommentRepository.findById(articleCommentId)
                .orElseThrow(() -> new ResourceNotFoundException(ARTICLE_COMMENT_NOT_FOUND));
    }

    private ArticleCommentReaction getArticleCommentReactionEntity(ArticleComment articleComment, Member member) {
        return articleCommentReactionRepository.findByArticleCommentAndMember(articleComment, member)
                .orElseThrow(() -> new ResourceNotFoundException(ARTICLE_COMMENT_REACTION_NOT_FOUND));
    }

    private void verifyReactionOwner(ArticleCommentReaction reaction, Member member) {
        if (!Objects.equals(reaction.getMember(), member))
            throw new UnauthorizedResourceAccessException();
    }

    private boolean checkReactionExistence(
            ArticleComment articleComment,
            Member member
    ) {
        return articleCommentReactionRepository.existsByArticleCommentAndMember(
                articleComment,
                member
        );
    }
}