package site.board.boardtraining.domain.hashtag.service;

import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.board.entity.Board;

import java.util.List;

public interface HashtagService {
    List<String> getAllBoardHashtags(Board board);

    void addBoardHashtags(List<String> boardHashtags, Board boardId);

    void deleteBoardHashtags(Board board);

    List<String> getAllArticleHashtags(Article article);

    void addArticleHashtags(List<String> articleHashtags, Article articleId);

    void deleteArticleHashtags(Article article);
}