package site.board.boardtraining.domain.board.dto.business;

import org.springframework.data.domain.Pageable;

public record SearchBoardsDto(
        Pageable pageable,
        String searchKeyword
) {
    public static SearchBoardsDto of(
            Pageable pageable,
            String searchKeyword
    ) {
        return new SearchBoardsDto(
                pageable,
                searchKeyword
        );
    }
}