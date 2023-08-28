package site.board.boardtraining.domain.reaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.board.boardtraining.domain.comment.entity.ArticleComment;
import site.board.boardtraining.domain.member.entity.Member;
import site.board.boardtraining.domain.reaction.constant.ReactionType;
import site.board.boardtraining.domain.reaction.entity.ArticleCommentReaction;

public interface ArticleCommentReactionRepository
        extends JpaRepository<ArticleCommentReaction, Long> {

    int countAllByTypeAndArticleComment(ReactionType type, ArticleComment articleComment);

    void deleteAllByArticleCommentAndMember(ArticleComment articleComment, Member member);

    boolean existsByTypeAndArticleCommentAndMember(ReactionType type, ArticleComment articleComment, Member member);
}