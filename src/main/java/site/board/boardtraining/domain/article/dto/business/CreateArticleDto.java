package site.board.boardtraining.domain.article.dto.business;

import site.board.boardtraining.domain.article.constant.ArticleStatus;
import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.board.entity.Board;
import site.board.boardtraining.domain.member.entity.Member;

public record CreateArticleDto(
        String title,
        String content,
        String thumbnailImageUrl,
        ArticleStatus status,
        Long boardId,
        Long memberId
) {
    public static CreateArticleDto of(
            String title,
            String content,
            String thumbnailImageUrl,
            ArticleStatus status,
            Long boardId,
            Long memberId
    ) {
        return new CreateArticleDto(
                title,
                content,
                thumbnailImageUrl,
                status,
                boardId,
                memberId
        );
    }

    public Article toEntity(
            Board board,
            Member member
    ) {
        return Article.of(
                title,
                content,
                thumbnailImageUrl,
                status,
                board,
                member
        );
    }
}