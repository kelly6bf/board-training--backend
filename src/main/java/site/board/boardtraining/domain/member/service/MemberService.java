package site.board.boardtraining.domain.member.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.board.boardtraining.domain.member.dto.CreateMemberRequest;
import site.board.boardtraining.domain.member.entity.Member;
import site.board.boardtraining.domain.member.repository.MemberRepository;

@Service
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public MemberService(
            PasswordEncoder passwordEncoder,
            MemberRepository memberRepository
    ) {
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
    }

    public Member getMemberByPersonalId(String personalId) {
        return memberRepository.findByPersonalId(personalId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));
    }

    public Member createMember(CreateMemberRequest request) {
        return memberRepository.save(
                Member.createUser(
                        request.personalId(),
                        passwordEncoder.encode(request.password()),
                        request.email(),
                        request.nickname()
                )
        );
    }
}