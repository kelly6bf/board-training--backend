package site.board.boardtraining.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.board.boardtraining.domain.comment.entity.ArticleComment;

public interface ArticleCommentRepository
        extends JpaRepository<ArticleComment, Long> {
}