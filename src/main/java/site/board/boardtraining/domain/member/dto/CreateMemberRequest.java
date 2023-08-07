package site.board.boardtraining.domain.member.dto;

public record CreateMemberRequest(
        String personalId,
        String password,
        String email,
        String nickname
) {
    public static CreateMemberRequest of(
            String personalId,
            String password,
            String email,
            String nickname
    ) {
        return new CreateMemberRequest(
                personalId,
                password,
                email,
                nickname
        );
    }
}