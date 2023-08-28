package site.board.boardtraining.domain.article.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.board.boardtraining.domain.article.constant.ArticleReactionType;
import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.article.entity.ArticleReaction;
import site.board.boardtraining.domain.article.exception.ArticleBusinessException;
import site.board.boardtraining.domain.article.repository.ArticleReactionRepository;
import site.board.boardtraining.domain.article.repository.ArticleRepository;
import site.board.boardtraining.domain.member.entity.Member;
import site.board.boardtraining.domain.member.repository.MemberRepository;
import site.board.boardtraining.global.exception.ResourceNotFoundException;
import site.board.boardtraining.global.exception.UnauthorizedResourceAccessException;

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

        if (checkReactionExistence(LIKE, article))
            throw new ArticleBusinessException(ARTICLE_LIKE_REACTION_ALREADY_EXIST);

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

        ArticleReaction articleReaction = articleReactionRepository.findByArticleAndMember(article, member)
                .orElseThrow(() -> new ResourceNotFoundException(ARTICLE_NOT_FOUND));

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

        if (checkReactionExistence(DISLIKE, article))
            throw new ArticleBusinessException(ARTICLE_DISLIKE_REACTION_ALREADY_EXIST);

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

        ArticleReaction articleReaction = articleReactionRepository.findByArticleAndMember(article, member)
                .orElseThrow(() -> new ResourceNotFoundException(ARTICLE_DISLIKE_REACTION_NOT_FOUND));

        verifyReactionOwner(articleReaction, member);

        articleReactionRepository.delete(articleReaction);
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

    private void verifyReactionOwner(ArticleReaction reaction, Member member) {
        if (!Objects.equals(reaction.getMember(), member))
            throw new UnauthorizedResourceAccessException();
    }

    @Transactional(readOnly = true)
    public Article getArticleEntity(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException(ARTICLE_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Member getMemberEntity(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException(MEMBER_NOT_FOUND));
    }
}