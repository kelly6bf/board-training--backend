package site.board.boardtraining.domain.member.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.board.boardtraining.domain.member.dto.CreateMemberRequest;
import site.board.boardtraining.domain.member.dto.CreateMemberResponse;
import site.board.boardtraining.domain.member.service.MemberService;
import site.board.boardtraining.global.response.success.SingleSuccessApiResponse;

@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<SingleSuccessApiResponse<CreateMemberResponse>> createMember(
            @RequestBody CreateMemberRequest request
    ) {
        return ResponseEntity.ok(
                SingleSuccessApiResponse.of(
                        "성공적으로 멤버가 생성되었습니다.",
                        CreateMemberResponse.from(
                                memberService.createMember(request)
                        )
                )
        );
    }
}