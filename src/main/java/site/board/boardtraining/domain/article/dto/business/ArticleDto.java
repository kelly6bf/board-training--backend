package site.board.boardtraining.domain.article.dto.business;

import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.member.dto.business.MemberDto;

import java.time.LocalDateTime;

public record ArticleDto(
        Long articleId,
        String title,
        String content,
        MemberDto member,
        LocalDateTime createdAt
) {
    public static ArticleDto of(
            Long articleId,
            String title,
            String content,
            MemberDto member,
            LocalDateTime createdAt
    ) {
        return new ArticleDto(
                articleId,
                title,
                content,
                member,
                createdAt
        );
    }

    public static ArticleDto from(Article article) {
        return new ArticleDto(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                MemberDto.from(article.getMember()),
                article.getCreatedAt()
        );
    }
}