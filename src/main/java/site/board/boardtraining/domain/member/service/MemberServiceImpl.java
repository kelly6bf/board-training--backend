package site.board.boardtraining.domain.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.board.boardtraining.domain.member.dto.business.CreateMemberDto;
import site.board.boardtraining.domain.member.entity.Member;
import site.board.boardtraining.domain.member.repository.MemberRepository;

@Service
public class MemberServiceImpl
        implements MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public MemberServiceImpl(
            PasswordEncoder passwordEncoder,
            MemberRepository memberRepository
    ) {
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
    }

    public Long createMember(CreateMemberDto dto) {
        return memberRepository.save(
                Member.createUser(
                        dto.personalId(),
                        passwordEncoder.encode(dto.password()),
                        dto.email(),
                        dto.nickname(),
                        dto.loginProvider()
                )
        )
                .getId();
    }
}