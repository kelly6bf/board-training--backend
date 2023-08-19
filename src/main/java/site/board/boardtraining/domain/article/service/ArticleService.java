package site.board.boardtraining.domain.article.service;

import org.springframework.data.domain.Page;
import site.board.boardtraining.domain.article.dto.business.*;

public interface ArticleService {

    Page<ArticleDto> searchArticles(SearchArticlesDto dto);

    ArticleDto getArticle(Long articleId);

    Long createArticle(CreateArticleDto dto);

    void updateArticle(UpdateArticleDto dto);

    void deleteArticle(DeleteArticleDto dto);
}