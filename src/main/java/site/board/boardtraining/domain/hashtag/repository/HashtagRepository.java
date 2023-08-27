package site.board.boardtraining.domain.hashtag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.board.boardtraining.domain.hashtag.entity.Hashtag;

public interface HashtagRepository
        extends JpaRepository<Hashtag, Long> {
}