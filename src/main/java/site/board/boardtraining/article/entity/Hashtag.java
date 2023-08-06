package site.board.boardtraining.article.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.board.boardtraining.global.audit.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "hashtag",
        indexes = {
                @Index(columnList = "title")
        }
)
@Entity
public class Hashtag
        extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String title;

    private Hashtag(String title) {
        this.title = title;
    }

    public static Hashtag of(String title) {
        return new Hashtag(title);
    }
}