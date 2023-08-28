package site.board.boardtraining.domain.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import site.board.boardtraining.domain.board.entity.Board;

public interface BoardRepository
        extends JpaRepository<Board, Long>,
        QuerydslPredicateExecutor<Board> {

    Page<Board> findByTitleContaining(String title, Pageable pageable);
}