package site.board.boardtraining.domain.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.board.boardtraining.domain.board.constant.BoardStatus;
import site.board.boardtraining.domain.member.entity.Member;
import site.board.boardtraining.global.audit.BaseTimeEntity;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board
        extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 100, nullable = false)
    private String introduction;

    @Column(length = 1000, nullable = false)
    private String thumbnailImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private BoardStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private Board(
            String title,
            String introduction,
            String thumbnailImageUrl,
            BoardStatus status,
            Member member
    ) {
        this.title = title;
        this.introduction = introduction;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.status = status;
        this.member = member;
    }

    public static Board of(
            String title,
            String introduction,
            String thumbnailImageUrl,
            BoardStatus status,
            Member member
    ) {
        return new Board(
                title,
                introduction,
                thumbnailImageUrl,
                status,
                member
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board board)) return false;
        return id != null && Objects.equals(id, board.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}