package site.board.boardtraining.domain.comment.service;

import site.board.boardtraining.domain.comment.dto.business.ArticleCommentsDto;
import site.board.boardtraining.domain.comment.dto.business.CreateArticleCommentDto;
import site.board.boardtraining.domain.comment.dto.business.DeleteArticleCommentDto;
import site.board.boardtraining.domain.comment.dto.business.UpdateArticleCommentDto;

import java.util.List;

public interface ArticleCommentService {

    List<ArticleCommentsDto> getArticleComments(Long articleId);

    void createParentArticleComment(CreateArticleCommentDto dto);

    void createChildArticleComment(CreateArticleCommentDto dto);

    void updateArticleComment(UpdateArticleCommentDto dto);

    void deleteArticleComment(DeleteArticleCommentDto dto);
}