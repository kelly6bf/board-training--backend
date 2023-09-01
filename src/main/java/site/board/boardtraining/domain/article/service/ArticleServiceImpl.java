package site.board.boardtraining.domain.article.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.board.boardtraining.domain.article.dto.business.*;
import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.article.repository.ArticleReactionRepository;
import site.board.boardtraining.domain.article.repository.ArticleRepository;
import site.board.boardtraining.domain.board.entity.Board;
import site.board.boardtraining.domain.board.repository.BoardRepository;
import site.board.boardtraining.domain.comment.repository.ArticleCommentRepository;
import site.board.boardtraining.domain.hashtag.service.HashtagService;
import site.board.boardtraining.domain.member.entity.Member;
import site.board.boardtraining.domain.member.repository.MemberRepository;
import site.board.boardtraining.global.exception.ResourceNotFoundException;
import site.board.boardtraining.global.exception.UnauthorizedResourceProcessException;

import java.util.Set;

import static site.board.boardtraining.domain.article.constant.ArticleReactionType.DISLIKE;
import static site.board.boardtraining.domain.article.constant.ArticleReactionType.LIKE;
import static site.board.boardtraining.domain.article.exception.ArticleErrorCode.ARTICLE_NOT_FOUND;
import static site.board.boardtraining.domain.article.exception.ArticleErrorCode.UNAUTHORIZED_ARTICLE_PROCESS;
import static site.board.boardtraining.domain.board.exception.BoardErrorCode.BOARD_NOT_FOUND;
import static site.board.boardtraining.domain.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;

@Transactional
@Service
public class ArticleServiceImpl
        implements ArticleService {
    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final ArticleReactionRepository articleReactionRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final HashtagService hashtagService;

    public ArticleServiceImpl(
            ArticleRepository articleRepository,
            BoardRepository boardRepository,
            MemberRepository memberRepository,
            HashtagService hashtagService,
            ArticleReactionRepository articleReactionRepository,
            ArticleCommentRepository articleCommentRepository
    ) {
        this.articleRepository = articleRepository;
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
        this.hashtagService = hashtagService;
        this.articleReactionRepository = articleReactionRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ArticleDto> searchArticles(SearchArticlesDto dto) {
        return findArticlesBySearchConditions(
                dto.searchKeyword(),
                dto.hashtags(),
                dto.pageable()
        )
                .map(article -> ArticleDto.from(
                        article,
                        hashtagService.getAllArticleHashtags(article),
                        articleReactionRepository.countAllByTypeAndArticle(LIKE, article),
                        articleReactionRepository.countAllByTypeAndArticle(DISLIKE, article),
                        articleCommentRepository.countAllByArticle(article)
                ));
    }

    @Transactional(readOnly = true)
    @Override
    public ArticleDto getArticle(Long articleId) {
        Article article = getArticleEntity(articleId);

        return ArticleDto.from(
                article,
                hashtagService.getAllArticleHashtags(article),
                articleReactionRepository.countAllByTypeAndArticle(LIKE, article),
                articleReactionRepository.countAllByTypeAndArticle(DISLIKE, article),
                articleCommentRepository.countAllByArticle(article)
        );
    }

    @Override
    public Long createArticle(CreateArticleDto dto) {
        Article createdArticle = articleRepository.save(
                dto.toEntity(
                        getBoardEntity(dto.boardId()),
                        getMemberEntity(dto.memberId())
                )
        );

        hashtagService.addArticleHashtags(
                dto.hashtags(),
                createdArticle
        );

        return createdArticle.getId();
    }

    @Override
    public void updateArticle(UpdateArticleDto dto) {
        Article savedArticle = getArticleEntity(dto.articleId());

        verifyArticleOwner(savedArticle, dto.memberId());

        savedArticle.update(
                dto.title(),
                dto.content(),
                dto.thumbnailImageUrl(),
                dto.status()
        );

        hashtagService.updateArticleHashtags(
                dto.hashtags(),
                savedArticle
        );
    }

    @Override
    public void deleteArticle(DeleteArticleDto dto) {
        Article savedArticle = getArticleEntity(dto.articleId());

        verifyArticleOwner(savedArticle, dto.memberId());

        hashtagService.deleteArticleHashtags(savedArticle);

        articleRepository.delete(savedArticle);
    }

    private Member getMemberEntity(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException(MEMBER_NOT_FOUND));
    }

    private Board getBoardEntity(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException(BOARD_NOT_FOUND));
    }

    private Article getArticleEntity(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException(ARTICLE_NOT_FOUND));
    }

    private void verifyArticleOwner(
            Article article,
            Long memberId
    ) {
        if (!article.getMember().getId().equals(memberId))
            throw new UnauthorizedResourceProcessException(UNAUTHORIZED_ARTICLE_PROCESS);
    }

    private Page<Article> findArticlesBySearchConditions(
            String searchKeyword,
            Set<String> hashtags,
            Pageable pageable
    ) {
        // keyword && hashtag
        if (!searchKeyword.isBlank() && !hashtags.isEmpty()) {
            return articleRepository.searchArticlesByKeywordAndHashtag(
                    searchKeyword,
                    hashtags,
                    pageable
            );
        } else if (!searchKeyword.isBlank()) {  // keyword search
            return articleRepository.searchArticlesByKeyword(
                    searchKeyword,
                    pageable
            );
        } else if (!hashtags.isEmpty()) {   // hashtag search
            return articleRepository.searchArticlesByHashtag(
                    hashtags,
                    pageable
            );
        } else {    // get all
            return articleRepository.findAll(pageable);
        }
    }
}