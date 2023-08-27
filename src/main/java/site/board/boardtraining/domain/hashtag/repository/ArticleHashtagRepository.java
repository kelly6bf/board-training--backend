package site.board.boardtraining.domain.hashtag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.hashtag.entity.ArticleHashtag;

import java.util.List;

public interface ArticleHashtagRepository
        extends JpaRepository<ArticleHashtag, Long> {

    List<ArticleHashtag> findAllByArticle(Article article);

    void deleteAllByArticle(Article article);
}