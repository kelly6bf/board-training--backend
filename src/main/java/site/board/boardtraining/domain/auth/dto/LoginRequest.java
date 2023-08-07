package site.board.boardtraining.domain.auth.dto;

public record LoginRequest(
        String personalId,
        String password
) {}
