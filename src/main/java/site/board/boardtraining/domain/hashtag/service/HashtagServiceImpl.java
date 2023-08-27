package site.board.boardtraining.domain.hashtag.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.board.entity.Board;
import site.board.boardtraining.domain.hashtag.entity.ArticleHashtag;
import site.board.boardtraining.domain.hashtag.entity.BoardHashtag;
import site.board.boardtraining.domain.hashtag.entity.Hashtag;
import site.board.boardtraining.domain.hashtag.repository.ArticleHashtagRepository;
import site.board.boardtraining.domain.hashtag.repository.BoardHashtagRepository;
import site.board.boardtraining.domain.hashtag.repository.HashtagRepository;

import java.util.List;

@Transactional
@Service
public class HashtagServiceImpl
        implements HashtagService {

    private final HashtagRepository hashtagRepository;
    private final BoardHashtagRepository boardHashtagRepository;
    private final ArticleHashtagRepository articleHashtagRepository;

    @Transactional(readOnly = true)
    @Override
    public List<String> getAllBoardHashtags(Board board) {
        return boardHashtagRepository.findAllByBoard(board)
                .stream()
                .map(BoardHashtag::getHashtag)
                .toList();
    }

    @Override
    public void addBoardHashtags(List<String> boardHashtags, Board board) {
        boardHashtags
                .stream()
                .map(hashtag -> hashtagRepository.findByTitle(hashtag)
                        .orElse(createHashtag(hashtag))
                )
                .map(hashtag -> boardHashtagRepository.save(
                        BoardHashtag.of(
                                hashtag,
                                board
                        )
                ));
    }

    @Override
    public void deleteBoardHashtags(Board board) {
        boardHashtagRepository.deleteAllByBoard(board);
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> getAllArticleHashtags(Article article) {
        return articleHashtagRepository.findAllByArticle(article)
                .stream()
                .map(ArticleHashtag::getHashtag)
                .toList();
    }

    @Override
    public void addArticleHashtags(List<String> articleHashtags, Article article) {
        articleHashtags
                .stream()
                .map(hashtag -> hashtagRepository.findByTitle(hashtag)
                        .orElse(createHashtag(hashtag))
                )
                .map(hashtag -> articleHashtagRepository.save(
                        ArticleHashtag.of(
                                hashtag,
                                article
                        )
                ));
    }

    @Override
    public void deleteArticleHashtags(Article article) {
        articleHashtagRepository.deleteAllByArticle(article);
    }

    public Hashtag createHashtag(String hashtagTitle) {
        return hashtagRepository.save(
                Hashtag.of(hashtagTitle)
        );
    }

    public HashtagServiceImpl(
            HashtagRepository hashtagRepository,
            BoardHashtagRepository boardHashtagRepository,
            ArticleHashtagRepository articleHashtagRepository
    ) {
        this.hashtagRepository = hashtagRepository;
        this.boardHashtagRepository = boardHashtagRepository;
        this.articleHashtagRepository = articleHashtagRepository;
    }
}