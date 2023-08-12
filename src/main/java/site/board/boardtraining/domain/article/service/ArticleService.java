package site.board.boardtraining.domain.article.service;

import site.board.boardtraining.domain.article.dto.business.ArticleDto;
import site.board.boardtraining.domain.article.dto.business.CreateArticleDto;
import site.board.boardtraining.domain.article.dto.business.DeleteArticleDto;
import site.board.boardtraining.domain.article.dto.business.UpdateArticleDto;

public interface ArticleService {
    ArticleDto getArticle(Long articleId);

    Long createArticle(CreateArticleDto dto);

    void updateArticle(UpdateArticleDto dto);

    void deleteArticle(DeleteArticleDto dto);
}