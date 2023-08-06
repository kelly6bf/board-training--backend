package site.board.boardtraining.member.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.board.boardtraining.member.entity.Member;
import site.board.boardtraining.member.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailService
        implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String personalId) throws UsernameNotFoundException {
        Member member = memberRepository.findByPersonalId(personalId)
                .orElseThrow(() -> new EntityNotFoundException("존재 하지 않는 사용자입니다."));

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(member.getRole().getKey());
        grantedAuthorityList.add(simpleGrantedAuthority);

        User principal = new User(member.getPersonalId(), member.getPassword(), grantedAuthorityList);


        return principal;
    }
}
