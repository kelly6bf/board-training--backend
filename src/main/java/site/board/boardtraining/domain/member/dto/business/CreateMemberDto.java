package site.board.boardtraining.domain.member.dto.business;

import site.board.boardtraining.domain.member.constant.LoginProvider;

public record CreateMemberDto(
        String personalId,
        String password,
        String email,
        String nickname,
        LoginProvider loginProvider
) {
    public static CreateMemberDto of(
            String personalId,
            String password,
            String email,
            String nickname,
            LoginProvider loginProvider
    ) {
        return new CreateMemberDto(
                personalId,
                password,
                email,
                nickname,
                loginProvider
        );
    }
}