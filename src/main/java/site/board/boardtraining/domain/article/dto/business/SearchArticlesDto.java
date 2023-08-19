package site.board.boardtraining.domain.article.dto.business;

import org.springframework.data.domain.Pageable;

public record SearchArticlesDto(
        Pageable pageable,
        Long boardId,
        String searchKeyword
) {
    public static SearchArticlesDto of(
            Pageable pageable,
            Long boardId,
            String searchKeyword
    ) {
        return new SearchArticlesDto(
                pageable,
                boardId,
                searchKeyword
        );
    }
}