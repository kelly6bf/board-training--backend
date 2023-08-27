package site.board.boardtraining.domain.hashtag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.board.boardtraining.domain.hashtag.entity.Hashtag;

import java.util.Optional;

public interface HashtagRepository
        extends JpaRepository<Hashtag, Long> {

    Optional<Hashtag> findByTitle(String title);
}