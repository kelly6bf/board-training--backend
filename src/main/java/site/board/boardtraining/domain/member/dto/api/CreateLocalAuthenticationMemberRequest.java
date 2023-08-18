package site.board.boardtraining.domain.member.dto.api;

import site.board.boardtraining.domain.member.dto.business.CreateMemberDto;

import static site.board.boardtraining.domain.member.constant.LoginProvider.LOCAL;

public record CreateLocalAuthenticationMemberRequest(
        String personalId,
        String password,
        String email,
        String nickname
) {
    public CreateMemberDto toDto() {
        return new CreateMemberDto(
                personalId,
                password,
                email,
                nickname,
                LOCAL
        );
    }
}