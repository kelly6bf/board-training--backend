package site.board.boardtraining.domain.reaction.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.board.boardtraining.domain.comment.entity.ArticleComment;
import site.board.boardtraining.domain.member.entity.Member;
import site.board.boardtraining.domain.reaction.constant.ReactionType;
import site.board.boardtraining.global.audit.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "article_comment_reaction")
@Entity
public class ArticleCommentReaction
        extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private ReactionType type;

    @ManyToOne(fetch = FetchType.LAZY)
    private ArticleComment articleComment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public ArticleCommentReaction(
            ReactionType type,
            ArticleComment articleComment,
            Member member
    ) {
        this.type = type;
        this.articleComment = articleComment;
        this.member = member;
    }

    public static ArticleCommentReaction of(
            ReactionType type,
            ArticleComment articleComment,
            Member member
    ) {
        return new ArticleCommentReaction(
                type,
                articleComment,
                member
        );
    }
}