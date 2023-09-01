package site.board.boardtraining.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.comment.constant.ArticleCommentType;
import site.board.boardtraining.domain.comment.entity.ArticleComment;

import java.util.List;

public interface ArticleCommentRepository
        extends JpaRepository<ArticleComment, Long> {

    void deleteAllByParentCommentId(Long parentCommentId);

    List<ArticleComment> findAllByArticleAndCommentType(Article article, ArticleCommentType commentType);

    List<ArticleComment> findAllByParentCommentId(Long parentCommentId);

    int countAllByArticle(Article article);
}