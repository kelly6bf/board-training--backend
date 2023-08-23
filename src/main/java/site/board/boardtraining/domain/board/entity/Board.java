package site.board.boardtraining.domain.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.board.boardtraining.domain.board.constant.BoardStatus;
import site.board.boardtraining.domain.member.entity.Member;
import site.board.boardtraining.global.audit.BaseTimeEntity;

import java.util.Objects;

import static site.board.boardtraining.domain.board.constant.BoardStatus.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board")
@Entity
public class Board
        extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 100, nullable = false)
    private String description;

    @Column(length = 1000, nullable = false)
    private String thumbnailImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private BoardStatus status = ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private Board(
            String title,
            String description,
            String thumbnailImageUrl,
            Member member
    ) {
        this.title = title;
        this.description = description;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.member = member;
    }

    public static Board of(
            String title,
            String description,
            String thumbnailImageUrl,
            Member member
    ) {
        return new Board(
                title,
                description,
                thumbnailImageUrl,
                member
        );
    }

    public void update(
            String title,
            String description,
            String thumbnailImageUrl
    ) {
        this.title = title;
        this.description = description;
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board that)) return false;
        return this.getId() != null && Objects.equals(this.getId(), that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}