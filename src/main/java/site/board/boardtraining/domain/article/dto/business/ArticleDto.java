package site.board.boardtraining.domain.article.dto.business;

import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.member.dto.business.MemberDto;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;

public record ArticleDto(
        Long articleId,
        String title,
        String content,
        String thumbnailImageUrl,
        LinkedHashSet<String> hashtags,
        Integer likeCount,
        Integer dislikeCount,
        Integer commentsCount,
        MemberDto member,
        LocalDateTime createdAt
) {
    public static ArticleDto of(
            Long articleId,
            String title,
            String content,
            String thumbnailImageUrl,
            LinkedHashSet<String> hashtags,
            Integer likeCount,
            Integer dislikeCount,
            Integer commentsCount,
            MemberDto member,
            LocalDateTime createdAt
    ) {
        return new ArticleDto(
                articleId,
                title,
                content,
                thumbnailImageUrl,
                hashtags,
                likeCount,
                dislikeCount,
                commentsCount,
                member,
                createdAt
        );
    }

    public static ArticleDto from(
            Article article,
            LinkedHashSet<String> hashtags,
            Integer likeCount,
            Integer dislikeCount,
            Integer commentsCount
    ) {
        return new ArticleDto(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getThumbnailImageUrl(),
                hashtags,
                likeCount,
                dislikeCount,
                commentsCount,
                MemberDto.from(article.getMember()),
                article.getCreatedAt()
        );
    }
}