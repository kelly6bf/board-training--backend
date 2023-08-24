package site.board.boardtraining.domain.comment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import site.board.boardtraining.domain.comment.dto.business.ArticleCommentsDto;
import site.board.boardtraining.domain.comment.dto.business.CreateArticleCommentDto;
import site.board.boardtraining.domain.comment.dto.business.DeleteArticleCommentDto;
import site.board.boardtraining.domain.comment.dto.business.UpdateArticleCommentDto;

public interface ArticleCommentService {

    Page<ArticleCommentsDto> getArticleComments(Long articleId, Pageable pageable);

    void createParentArticleComment(CreateArticleCommentDto dto);

    void createChildArticleComment(CreateArticleCommentDto dto);

    void updateArticleComment(UpdateArticleCommentDto dto);

    void deleteArticleComment(DeleteArticleCommentDto dto);
}