package site.board.boardtraining.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.board.boardtraining.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepository
        extends JpaRepository<Member, Long> {
    Optional<Member> findByPersonalId(String personalId);
}