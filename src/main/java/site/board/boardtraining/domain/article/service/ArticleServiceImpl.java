package site.board.boardtraining.domain.article.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.board.boardtraining.domain.article.dto.business.ArticleDto;
import site.board.boardtraining.domain.article.dto.business.CreateArticleDto;
import site.board.boardtraining.domain.article.dto.business.DeleteArticleDto;
import site.board.boardtraining.domain.article.dto.business.UpdateArticleDto;
import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.article.repository.ArticleRepository;
import site.board.boardtraining.domain.member.entity.Member;
import site.board.boardtraining.domain.member.repository.MemberRepository;
import site.board.boardtraining.global.exception.ResourceNotFoundException;
import site.board.boardtraining.global.exception.UnauthorizedResourceAccessException;

import static site.board.boardtraining.domain.article.exception.ArticleErrorCode.ARTICLE_NOT_FOUND;
import static site.board.boardtraining.domain.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;

@Transactional
@Service
public class ArticleServiceImpl
        implements ArticleService {
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public ArticleServiceImpl(
            ArticleRepository articleRepository,
            MemberRepository memberRepository
    ) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public ArticleDto getArticle(Long articleId) {
        return ArticleDto.from(
                getArticleEntity(articleId)
        );
    }

    @Override
    public Long createArticle(CreateArticleDto dto) {
        Article createdArticle = articleRepository.save(
                Article.of(
                        dto.title(),
                        dto.content(),
                        dto.status(),
                        getMemberEntity(dto.memberPersonalId())
                )
        );

        return createdArticle.getId();
    }

    @Override
    public void updateArticle(UpdateArticleDto dto) {
        Article savedArticle = getArticleEntity(dto.articleId());

        verifyArticleOwner(savedArticle, dto.memberPersonalId());

        savedArticle.update(
                dto.title(),
                dto.content(),
                dto.status()
        );
    }

    @Override
    public void deleteArticle(DeleteArticleDto dto) {
        Article savedArticle = getArticleEntity(dto.articleId());

        verifyArticleOwner(savedArticle, dto.memberPersonalId());

        articleRepository.delete(savedArticle);
    }

    private void verifyArticleOwner(
            Article article,
            String memberPersonalId
    ) {
        Member member = getMemberEntity(memberPersonalId);

        if (!article.getMember().equals(member))
            throw new UnauthorizedResourceAccessException();
    }

    private Member getMemberEntity(String memberPersonalId) {
        return memberRepository.findByPersonalId(memberPersonalId)
                .orElseThrow(() -> new ResourceNotFoundException(MEMBER_NOT_FOUND));
    }

    private Article getArticleEntity(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException(ARTICLE_NOT_FOUND));
    }
}