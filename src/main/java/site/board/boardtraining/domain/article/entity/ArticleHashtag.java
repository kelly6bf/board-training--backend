package site.board.boardtraining.domain.article.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.board.boardtraining.global.audit.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "article_hashtag")
@Entity
public class ArticleHashtag
        extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Hashtag hashtag;

    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    private ArticleHashtag(
            Hashtag hashtag,
            Article article
    ) {
        this.hashtag = hashtag;
        this.article = article;
    }

    public static ArticleHashtag of(
            Hashtag hashtag,
            Article article
    ) {
        return new ArticleHashtag(hashtag, article);
    }
}