package site.board.boardtraining.domain.article.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.board.boardtraining.domain.article.dto.business.*;
import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.article.repository.ArticleRepository;
import site.board.boardtraining.domain.board.repository.BoardRepository;
import site.board.boardtraining.domain.member.repository.MemberRepository;
import site.board.boardtraining.global.exception.ResourceNotFoundException;
import site.board.boardtraining.global.exception.UnauthorizedResourceAccessException;

import static site.board.boardtraining.domain.article.exception.ArticleErrorCode.ARTICLE_NOT_FOUND;

@Transactional
@Service
public class ArticleServiceImpl
        implements ArticleService {
    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public ArticleServiceImpl(
            ArticleRepository articleRepository,
            BoardRepository boardRepository,
            MemberRepository memberRepository
    ) {
        this.articleRepository = articleRepository;
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ArticleDto> searchArticles(SearchArticlesDto dto) {
        if (dto.searchKeyword() == null || dto.searchKeyword().isBlank()) {
            return articleRepository.findAll(dto.pageable())
                    .map(ArticleDto::from);
        }

        return articleRepository.findByBoard_IdAndTitleContainingOrContentContaining(
                        dto.boardId(),
                        dto.searchKeyword(),
                        dto.searchKeyword(),
                        dto.pageable()
                )
                .map(ArticleDto::from);
    }

    @Transactional(readOnly = true)
    @Override
    public ArticleDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleDto::from)
                .orElseThrow(() -> new ResourceNotFoundException(ARTICLE_NOT_FOUND));
    }

    @Override
    public Long createArticle(CreateArticleDto dto) {
        Article createdArticle = articleRepository.save(
                dto.toEntity(
                        boardRepository.getReferenceById(dto.boardId()),
                        memberRepository.getReferenceById(dto.memberId())
                )
        );

        return createdArticle.getId();
    }

    @Override
    public void updateArticle(UpdateArticleDto dto) {
        Article savedArticle = articleRepository.getReferenceById(dto.articleId());

        verifyArticleOwner(savedArticle, dto.memberId());

        savedArticle.update(
                dto.title(),
                dto.content(),
                dto.thumbnailImageUrl(),
                dto.status()
        );
    }

    @Override
    public void deleteArticle(DeleteArticleDto dto) {
        Article savedArticle = articleRepository.getReferenceById(dto.articleId());

        verifyArticleOwner(savedArticle, dto.memberId());

        articleRepository.delete(savedArticle);
    }

    private void verifyArticleOwner(
            Article article,
            Long memberId
    ) {
        if (!article.getMember().getId().equals(memberId))
            throw new UnauthorizedResourceAccessException();
    }
}