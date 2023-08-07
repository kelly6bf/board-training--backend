package site.board.boardtraining.member.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import site.board.boardtraining.member.entity.Member;
import site.board.boardtraining.member.repository.MemberRepository;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member getMemberByPersonalId(String personalId) {
        return memberRepository.findByPersonalId(personalId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));
    }
}