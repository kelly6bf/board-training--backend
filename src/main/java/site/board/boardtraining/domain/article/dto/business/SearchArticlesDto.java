package site.board.boardtraining.domain.article.dto.business;

import org.springframework.data.domain.Pageable;

import java.util.Set;

public record SearchArticlesDto(
        Long boardId,
        String searchKeyword,
        Set<String> hashtags,
        Pageable pageable
) {
    public static SearchArticlesDto of(
            Long boardId,
            String searchKeyword,
            Set<String> hashtags,
            Pageable pageable
    ) {
        return new SearchArticlesDto(
                boardId,
                searchKeyword,
                hashtags,
                pageable
        );
    }
}