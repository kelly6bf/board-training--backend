package site.board.boardtraining.domain.hashtag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.board.boardtraining.domain.hashtag.entity.ArticleHashtag;

public interface ArticleHashtagRepository
        extends JpaRepository<ArticleHashtag, Long> {
}