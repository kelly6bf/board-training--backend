package site.board.boardtraining.auth.dto;

public record LoginRequest(
        String personalId,
        String password
) {}
