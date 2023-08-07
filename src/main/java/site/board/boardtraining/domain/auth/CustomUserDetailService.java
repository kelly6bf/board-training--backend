package site.board.boardtraining.domain.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.board.boardtraining.domain.auth.data.CustomUserPrincipal;
import site.board.boardtraining.domain.member.entity.Member;
import site.board.boardtraining.domain.member.service.MemberService;

@Service
public class CustomUserDetailService
        implements UserDetailsService {

    private final MemberService memberService;

    public CustomUserDetailService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public UserDetails loadUserByUsername(String personalId)
            throws UsernameNotFoundException {

        Member member = memberService.getMemberByPersonalId(personalId);

        return CustomUserPrincipal.from(member);
    }
}