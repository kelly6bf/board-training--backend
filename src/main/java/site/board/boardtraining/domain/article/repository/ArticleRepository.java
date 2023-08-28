package site.board.boardtraining.domain.article.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.board.entity.Board;

public interface ArticleRepository
        extends JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article> {

    Page<Article> findByBoardAndTitleContainingOrContentContaining(Board board, String title, String content, Pageable pageable);
}