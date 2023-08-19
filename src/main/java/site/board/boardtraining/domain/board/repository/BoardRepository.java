package site.board.boardtraining.domain.board.repository;

import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import site.board.boardtraining.domain.board.entity.Board;
import site.board.boardtraining.domain.board.entity.QBoard;

public interface BoardRepository
        extends JpaRepository<Board, Long>,
        QuerydslPredicateExecutor<Board>,
        QuerydslBinderCustomizer<QBoard> {

    Page<Board> findByTitleContaining(String title, Pageable pageable);

    @Override
    default void customize(
            QuerydslBindings bindings,
            QBoard root
    ) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(
                root.title
        );
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
    }
}