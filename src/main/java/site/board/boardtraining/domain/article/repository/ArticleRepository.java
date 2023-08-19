package site.board.boardtraining.domain.article.repository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.article.entity.QArticle;

public interface ArticleRepository
        extends JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>,
        QuerydslBinderCustomizer<QArticle> {

    Page<Article> findByBoard_IdAndTitleContainingOrContentContaining(Long boardId, String title, String content, Pageable pageable);

    @Override
    default void customize(
            QuerydslBindings bindings,
            QArticle root
    ) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(
                root.title,
                root.content,
                root.board,
                root.createdAt
        );
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.board).first(SimpleExpression::eq);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
    }
}