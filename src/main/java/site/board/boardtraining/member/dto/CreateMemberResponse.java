package site.board.boardtraining.member.dto;

import site.board.boardtraining.member.entity.Member;

public record CreateMemberResponse(
        Long memberId
) {
    public static CreateMemberResponse from(Member member) {
        return new CreateMemberResponse(member.getId());
    }
}