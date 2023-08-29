package site.board.boardtraining.domain.article.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.board.boardtraining.domain.article.dto.business.*;
import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.article.repository.ArticleRepository;
import site.board.boardtraining.domain.board.entity.Board;
import site.board.boardtraining.domain.board.repository.BoardRepository;
import site.board.boardtraining.domain.hashtag.service.HashtagService;
import site.board.boardtraining.domain.member.entity.Member;
import site.board.boardtraining.domain.member.repository.MemberRepository;
import site.board.boardtraining.global.exception.ResourceNotFoundException;
import site.board.boardtraining.global.exception.UnauthorizedResourceAccessException;

import static site.board.boardtraining.domain.article.exception.ArticleErrorCode.ARTICLE_NOT_FOUND;
import static site.board.boardtraining.domain.board.exception.BoardErrorCode.BOARD_NOT_FOUND;
import static site.board.boardtraining.domain.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;

@Transactional
@Service
public class ArticleServiceImpl
        implements ArticleService {
    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final HashtagService hashtagService;
    private final ArticleReactionService articleReactionService;

    public ArticleServiceImpl(
            ArticleRepository articleRepository,
            BoardRepository boardRepository,
            MemberRepository memberRepository,
            HashtagService hashtagService,
            ArticleReactionService articleReactionService
    ) {
        this.articleRepository = articleRepository;
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
        this.hashtagService = hashtagService;
        this.articleReactionService = articleReactionService;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ArticleDto> searchArticles(SearchArticlesDto dto) {
        if (dto.searchKeyword() == null || dto.searchKeyword().isBlank()) {
            return articleRepository.findAll(dto.pageable())
                    .map(article ->
                            ArticleDto.from(
                                    article,
                                    hashtagService.getAllArticleHashtags(article),
                                    articleReactionService.getArticleLikeCount(article),
                                    articleReactionService.getArticleDislikeCount(article)
                            )
                    );
        }

        return articleRepository.findByBoardAndTitleContainingOrContentContaining(
                        getBoardEntity(dto.boardId()),
                        dto.searchKeyword(),
                        dto.searchKeyword(),
                        dto.pageable()
                )
                .map(article ->
                        ArticleDto.from(
                                article,
                                hashtagService.getAllArticleHashtags(article),
                                articleReactionService.getArticleLikeCount(article),
                                articleReactionService.getArticleDislikeCount(article)
                        )
                );
    }

    @Transactional(readOnly = true)
    @Override
    public ArticleDto getArticle(Long articleId) {
        Article article = getArticleEntity(articleId);

        return ArticleDto.from(
                article,
                hashtagService.getAllArticleHashtags(article),
                articleReactionService.getArticleLikeCount(article),
                articleReactionService.getArticleDislikeCount(article)
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
            throw new UnauthorizedResourceAccessException();
    }
}