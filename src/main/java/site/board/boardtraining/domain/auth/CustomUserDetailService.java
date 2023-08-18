package site.board.boardtraining.domain.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.board.boardtraining.domain.auth.data.CustomUserPrincipal;
import site.board.boardtraining.domain.member.entity.Member;
import site.board.boardtraining.domain.member.repository.MemberRepository;
import site.board.boardtraining.global.exception.ResourceNotFoundException;

import static site.board.boardtraining.domain.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;

@Service
public class CustomUserDetailService
        implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String personalId)
            throws UsernameNotFoundException {

        Member member = memberRepository.findByPersonalId(personalId)
                .orElseThrow(() -> new ResourceNotFoundException(MEMBER_NOT_FOUND));

        return CustomUserPrincipal.from(member);
    }
}