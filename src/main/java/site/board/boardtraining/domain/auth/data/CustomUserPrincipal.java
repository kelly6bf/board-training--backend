package site.board.boardtraining.domain.auth.data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import site.board.boardtraining.domain.member.constant.MemberRole;
import site.board.boardtraining.domain.member.constant.MemberStatus;
import site.board.boardtraining.domain.member.entity.Member;

import java.util.Collection;
import java.util.List;

import static site.board.boardtraining.domain.member.constant.MemberStatus.ACTIVE;

public class CustomUserPrincipal
        implements UserDetails {
    private String personalId;
    private String password;
    private MemberStatus memberStatus;
    private Collection<? extends GrantedAuthority> authorities;

    private CustomUserPrincipal(
            String personalId,
            String password,
            MemberStatus memberStatus,
            Collection<? extends GrantedAuthority> authorities
    ) {
        this.personalId = personalId;
        this.password = password;
        this.memberStatus = memberStatus;
        this.authorities = authorities;
    }

    public static CustomUserPrincipal from(Member member) {
        return new CustomUserPrincipal(
                member.getPersonalId(),
                member.getPassword(),
                member.getStatus(),
                convertMemberRoleToAuthorities(member.getRole())
        );
    }

    private static Collection<GrantedAuthority> convertMemberRoleToAuthorities(MemberRole role) {
        return List.of(
                new SimpleGrantedAuthority(role.getKey())
        );
    }

    private boolean isActive() {
        return memberStatus.equals(ACTIVE);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return personalId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive();
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    @Override
    public String toString() {
        return "CustomUserPrincipal{" +
                "personalId='" + personalId + '\'' +
                ", password='" + "[PROTECTED]" + '\'' +
                ", memberStatus=" + memberStatus +
                ", authorities=" + authorities +
                '}';
    }
}