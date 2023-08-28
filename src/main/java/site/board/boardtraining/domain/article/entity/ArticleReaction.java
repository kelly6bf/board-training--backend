package site.board.boardtraining.domain.article.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.board.boardtraining.domain.article.constant.ArticleReactionType;
import site.board.boardtraining.domain.member.entity.Member;
import site.board.boardtraining.global.audit.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "article_reaction")
@Entity
public class ArticleReaction
        extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private ArticleReactionType type;

    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public ArticleReaction(
            ArticleReactionType type,
            Article article,
            Member member
    ) {
        this.type = type;
        this.article = article;
        this.member = member;
    }

    public static ArticleReaction of(
            ArticleReactionType type,
            Article article,
            Member member
    ) {
        return new ArticleReaction(
                type,
                article,
                member
        );
    }
}