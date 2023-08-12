package site.board.boardtraining.domain.member.dto.business;

import site.board.boardtraining.domain.member.entity.Member;

import java.time.LocalDateTime;

public record MemberDto(
        Long memberId,
        String personalId,
        String email,
        String nickname,
        String introduction,
        LocalDateTime createdAt
) {
    public static MemberDto from(
            Member member
    ) {
        return new MemberDto(
                member.getId(),
                member.getPersonalId(),
                member.getEmail(),
                member.getNickname(),
                member.getIntroduction(),
                member.getCreatedAt()
        );
    }
}