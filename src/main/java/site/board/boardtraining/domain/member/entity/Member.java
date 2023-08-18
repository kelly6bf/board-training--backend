package site.board.boardtraining.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.board.boardtraining.domain.member.constant.LoginProvider;
import site.board.boardtraining.domain.member.constant.MemberStatus;
import site.board.boardtraining.global.audit.BaseTimeEntity;
import site.board.boardtraining.domain.member.constant.MemberRole;

import static site.board.boardtraining.domain.member.constant.MemberRole.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@Entity
public class Member
        extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true, updatable = false)
    private String personalId;

    @Column(length = 500, nullable = false)
    private String password;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 50, nullable = false, unique = true)
    private String nickname;

    @Column(length = 300, nullable = false)
    private String introduction = "";

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private LoginProvider loginProvider;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private MemberStatus status = MemberStatus.ACTIVE;

    private Member(
            String personalId,
            String password,
            String email,
            String nickname,
            LoginProvider loginProvider,
            MemberRole role
    ) {
        this.personalId = personalId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.loginProvider = loginProvider;
        this.role = role;
    }

    public static Member createUser(
            String personalId,
            String password,
            String email,
            String nickname,
            LoginProvider loginProvider
    ) {
        return new Member(
                personalId,
                password,
                email,
                nickname,
                loginProvider,
                USER
        );
    }
}