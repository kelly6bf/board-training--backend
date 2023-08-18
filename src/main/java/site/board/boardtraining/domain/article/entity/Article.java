package site.board.boardtraining.domain.article.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.board.boardtraining.domain.article.constant.ArticleStatus;
import site.board.boardtraining.global.audit.BaseEntity;
import site.board.boardtraining.domain.member.entity.Member;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "article",
        indexes = {
                @Index(columnList = "title"),
                @Index(columnList = "content"),
                @Index(columnList = "createdAt"),
        }
)
@Entity
public class Article
        extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private ArticleStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private Article(
            String title,
            String content,
            ArticleStatus status,
            Member member
    ) {
        this.title = title;
        this.content = content;
        this.status = status;
        this.member = member;
    }

    public static Article of(
            String title,
            String content,
            ArticleStatus status,
            Member member
    ) {
        return new Article(
                title,
                content,
                status,
                member
        );
    }

    public void update(
            String title,
            String content,
            ArticleStatus status
    ) {
        this.title = title;
        this.content = content;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}