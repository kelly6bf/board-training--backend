package site.board.boardtraining.domain.hashtag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.board.boardtraining.domain.hashtag.entity.BoardHashtag;

public interface BoardHashtagRepository
        extends JpaRepository<BoardHashtag, Long> {
}