package site.board.boardtraining.domain.member.dto;

import site.board.boardtraining.domain.member.entity.Member;

public record CreateMemberResponse(
        Long memberId
) {
    public static CreateMemberResponse from(Member member) {
        return new CreateMemberResponse(member.getId());
    }
}