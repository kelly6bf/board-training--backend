package site.board.boardtraining.domain.article.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.article.entity.ArticleReaction;
import site.board.boardtraining.domain.article.exception.ArticleBusinessException;
import site.board.boardtraining.domain.article.repository.ArticleReactionRepository;
import site.board.boardtraining.domain.article.repository.ArticleRepository;
import site.board.boardtraining.domain.member.entity.Member;
import site.board.boardtraining.domain.member.repository.MemberRepository;
import site.board.boardtraining.global.exception.ResourceNotFoundException;
import site.board.boardtraining.global.exception.UnauthorizedResourceProcessException;

import java.util.Objects;

import static site.board.boardtraining.domain.article.constant.ArticleReactionType.DISLIKE;
import static site.board.boardtraining.domain.article.constant.ArticleReactionType.LIKE;
import static site.board.boardtraining.domain.article.exception.ArticleErrorCode.*;
import static site.board.boardtraining.domain.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;

@Transactional
@Service
public class ArticleReactionServiceImpl
        implements ArticleReactionService {

    private final ArticleRepository articleRepository;
    private final ArticleReactionRepository articleReactionRepository;
    private final MemberRepository memberRepository;

    public ArticleReactionServiceImpl(
            ArticleRepository articleRepository,
            ArticleReactionRepository articleReactionRepository,
            MemberRepository memberRepository
    ) {
        this.articleRepository = articleRepository;
        this.articleReactionRepository = articleReactionRepository;
        this.memberRepository = memberRepository;
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
    public void addArticleLike(Long articleId, Long memberId) {

        Article article = getArticleEntity(articleId);
        Member member = getMemberEntity(memberId);

        if (checkReactionExistence(article, member))
            throw new ArticleBusinessException(ARTICLE_REACTION_ALREADY_EXIST);

        articleReactionRepository.save(
                ArticleReaction.of(
                        LIKE,
                        article,
                        getMemberEntity(memberId)
                )
        );
    }

    @Override
    public void deleteArticleLike(Long articleId, Long memberId) {

        Article article = getArticleEntity(articleId);
        Member member = getMemberEntity(memberId);
        ArticleReaction articleReaction = getArticleReactionEntity(article, member);

        verifyReactionOwner(articleReaction, member);

        articleReactionRepository.delete(articleReaction);
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
    public void addArticleDislike(Long articleId, Long memberId) {

        Article article = getArticleEntity(articleId);
        Member member = getMemberEntity(memberId);

        if (checkReactionExistence(article, member))
            throw new ArticleBusinessException(ARTICLE_REACTION_ALREADY_EXIST);

        articleReactionRepository.save(
                ArticleReaction.of(
                        DISLIKE,
                        article,
                        getMemberEntity(memberId)
                )
        );
    }

    @Override
    public void deleteArticleDislike(Long articleId, Long memberId) {

        Article article = getArticleEntity(articleId);
        Member member = getMemberEntity(memberId);
        ArticleReaction articleReaction = getArticleReactionEntity(article, member);

        verifyReactionOwner(articleReaction, member);

        articleReactionRepository.delete(articleReaction);
    }

    private Member getMemberEntity(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException(MEMBER_NOT_FOUND));
    }

    private Article getArticleEntity(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException(ARTICLE_NOT_FOUND));
    }

    private ArticleReaction getArticleReactionEntity(Article article, Member member) {
        return articleReactionRepository.findByArticleAndMember(article, member)
                .orElseThrow(() -> new ResourceNotFoundException(ARTICLE_REACTION_NOT_FOUND));
    }

    private void verifyReactionOwner(ArticleReaction reaction, Member member) {
        if (!Objects.equals(reaction.getMember(), member))
            throw new UnauthorizedResourceProcessException(UNAUTHORIZED_ARTICLE_REACTION_PROCESS);
    }

    private boolean checkReactionExistence(
            Article article,
            Member member
    ) {
        return articleReactionRepository.existsByArticleAndMember(
                article,
                member
        );
    }
}