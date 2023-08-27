package site.board.boardtraining.domain.hashtag.service;

import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.board.entity.Board;

import java.util.Set;

public interface HashtagService {
    Set<String> getAllBoardHashtags(Board board);

    void addBoardHashtags(Set<String> boardHashtags, Board board);

    void updateBoardHashtags(Set<String> boardHashtags, Board board);

    void deleteBoardHashtags(Board board);

    Set<String> getAllArticleHashtags(Article article);

    void addArticleHashtags(Set<String> articleHashtags, Article article);

    void updateArticleHashtags(Set<String> articleHashtags, Article article);

    void deleteArticleHashtags(Article article);
}