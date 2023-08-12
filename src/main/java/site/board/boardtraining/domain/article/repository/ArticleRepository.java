package site.board.boardtraining.domain.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.board.boardtraining.domain.article.entity.Article;

public interface ArticleRepository
        extends JpaRepository<Article, Long> {
}