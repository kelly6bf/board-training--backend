package site.board.boardtraining.domain.hashtag.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.board.boardtraining.domain.board.entity.Board;
import site.board.boardtraining.global.audit.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board_hashtag")
@Entity
public class BoardHashtag
        extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Hashtag hashtag;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    private BoardHashtag(
            Hashtag hashtag,
            Board board
    ) {
        this.hashtag = hashtag;
        this.board = board;
    }

    public static BoardHashtag of(
            Hashtag hashtag,
            Board board
    ) {
        return new BoardHashtag(hashtag, board);
    }

    public String getHashtag() {
        return hashtag.getTitle();
    }
}