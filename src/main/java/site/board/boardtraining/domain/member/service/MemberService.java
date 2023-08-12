package site.board.boardtraining.domain.member.service;

import site.board.boardtraining.domain.member.dto.business.CreateMemberDto;

public interface MemberService {
    Long createMember(CreateMemberDto dto);
}