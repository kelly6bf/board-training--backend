package site.board.boardtraining.domain.article.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.board.boardtraining.domain.article.dto.business.ArticleDto;
import site.board.boardtraining.domain.article.dto.business.CreateArticleDto;
import site.board.boardtraining.domain.article.dto.business.DeleteArticleDto;
import site.board.boardtraining.domain.article.dto.business.UpdateArticleDto;
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
    public ArticleDto getArticle(Long articleId) {
        return ArticleDto.from(
                findArticleEntity(articleId)
        );
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

    private Article findArticleEntity(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException(ARTICLE_NOT_FOUND));
    }
}