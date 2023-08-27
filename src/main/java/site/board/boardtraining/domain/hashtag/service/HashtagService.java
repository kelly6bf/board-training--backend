package site.board.boardtraining.domain.hashtag.service;

import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.board.entity.Board;

import java.util.LinkedHashSet;

public interface HashtagService {
    LinkedHashSet<String> getAllBoardHashtags(Board board);

    void addBoardHashtags(LinkedHashSet<String> boardHashtags, Board board);

    void updateBoardHashtags(LinkedHashSet<String> boardHashtags, Board board);

    void deleteBoardHashtags(Board board);

    LinkedHashSet<String> getAllArticleHashtags(Article article);

    void addArticleHashtags(LinkedHashSet<String> articleHashtags, Article article);

    void updateArticleHashtags(LinkedHashSet<String> articleHashtags, Article article);

    void deleteArticleHashtags(Article article);
}