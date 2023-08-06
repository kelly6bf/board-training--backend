package site.board.boardtraining.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.board.boardtraining.member.entity.Member;

import java.util.Optional;

public interface MemberRepository
        extends JpaRepository<Member, Long> {

    Optional<Member> findByPersonalId(String personalId);
}
