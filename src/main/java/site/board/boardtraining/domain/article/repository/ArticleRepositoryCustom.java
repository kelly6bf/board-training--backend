package site.board.boardtraining.domain.article.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import site.board.boardtraining.domain.article.entity.Article;

import java.util.Set;

public interface ArticleRepositoryCustom {

    Page<Article> searchArticlesByKeyword(String keyword, Pageable pageable);

    Page<Article> searchArticlesByHashtag(Set<String> hashtags, Pageable pageable);

    Page<Article> searchArticlesByKeywordAndHashtag(String keyword, Set<String> hashtags, Pageable pageable);
}