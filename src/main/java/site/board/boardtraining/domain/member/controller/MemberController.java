package site.board.boardtraining.domain.member.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.board.boardtraining.domain.member.dto.business.CreateMemberDto;
import site.board.boardtraining.domain.member.dto.api.CreateMemberRequest;
import site.board.boardtraining.domain.member.service.MemberService;
import site.board.boardtraining.global.response.success.SingleSuccessApiResponse;
import site.board.boardtraining.global.response.success.SuccessApiResponse;

@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<SuccessApiResponse> createMember(
            @RequestBody CreateMemberRequest request
    ) {
        memberService.createMember(
                CreateMemberDto.of(
                        request.personalId(),
                        request.password(),
                        request.email(),
                        request.nickname()
                )
        );

        return ResponseEntity.ok(
                SingleSuccessApiResponse.of(
                        "성공적으로 회원 정보가 등록되었습니다."
                )
        );
    }
}