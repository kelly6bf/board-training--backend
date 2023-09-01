package site.board.boardtraining.domain.article.repository;

import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.article.entity.QArticle;
import site.board.boardtraining.domain.hashtag.entity.QArticleHashtag;
import site.board.boardtraining.domain.hashtag.entity.QHashtag;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ArticleRepositoryCustomImpl
        extends QuerydslRepositorySupport
        implements ArticleRepositoryCustom {
    public ArticleRepositoryCustomImpl() {
        super(Article.class);
    }

    @Override
    public Page<Article> searchArticlesByKeyword(
            String keyword,
            Pageable pageable
    ) {
        QArticle article = QArticle.article;

        JPQLQuery<Article> query = from(article)
                .where(
                        article.title.containsIgnoreCase(keyword)
                                .or(article.content.containsIgnoreCase(keyword))
                )
                .groupBy(article.id, article.createdAt);
        List<Article> articles = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();

        return new PageImpl<>(articles, pageable, query.fetchCount());
    }

    @Override
    public Page<Article> searchArticlesByHashtag(
            Set<String> hashtags,
            Pageable pageable
    ) {
        QArticle article = QArticle.article;
        QHashtag hashtag = QHashtag.hashtag;
        QArticleHashtag articleHashtag = QArticleHashtag.articleHashtag;

        JPQLQuery<Article> query = from(article)
                .join(articleHashtag).on(article.id.eq(articleHashtag.article.id))
                .join(hashtag).on(articleHashtag.hashtag.id.eq(hashtag.id))
                .where(hashtag.title.in(hashtags))
                .groupBy(article.id, article.createdAt);
        List<Article> articles = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();

        return new PageImpl<>(articles, pageable, query.fetchCount());
    }

    @Override
    public Page<Article> searchArticlesByKeywordAndHashtag(
            String keyword,
            Set<String> hashtags,
            Pageable pageable
    ) {
        QArticle article = QArticle.article;
        QHashtag hashtag = QHashtag.hashtag;
        QArticleHashtag articleHashtag = QArticleHashtag.articleHashtag;

        JPQLQuery<Article> query = from(article)
                .join(articleHashtag).on(article.id.eq(articleHashtag.article.id))
                .join(hashtag).on(articleHashtag.hashtag.id.eq(hashtag.id))
                .where(
                        article.title.containsIgnoreCase(keyword)
                                .or(article.content.containsIgnoreCase(keyword))
                                .and(hashtag.title.in(hashtags))
                )
                .groupBy(article.id, article.createdAt);
        List<Article> articles = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();

        return new PageImpl<>(articles, pageable, query.fetchCount());
    }
}