package site.board.boardtraining.member.controller;

import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.board.boardtraining.member.dto.CreateMemberRequest;
import site.board.boardtraining.member.dto.CreateMemberResponse;
import site.board.boardtraining.member.service.MemberService;

@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Transactional
    @PostMapping
    public CreateMemberResponse createMember(@RequestBody CreateMemberRequest request) {
        return CreateMemberResponse.from(
                memberService.createMember(request)
        );
    }
}