package site.board.boardtraining.domain.reaction.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.board.boardtraining.domain.board.entity.Board;
import site.board.boardtraining.domain.member.entity.Member;
import site.board.boardtraining.domain.reaction.constant.ReactionType;
import site.board.boardtraining.global.audit.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board_reaction")
@Entity
public class BoardReaction
        extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private ReactionType type;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private BoardReaction(
            ReactionType type,
            Board board,
            Member member
    ) {
        this.type = type;
        this.board = board;
        this.member = member;
    }

    public static BoardReaction of(
            ReactionType type,
            Board board,
            Member member
    ) {
        return new BoardReaction(
                type,
                board,
                member
        );
    }
}