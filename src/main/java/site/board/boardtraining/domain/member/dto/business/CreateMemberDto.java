package site.board.boardtraining.domain.member.dto.business;

public record CreateMemberDto(
        String personalId,
        String password,
        String email,
        String nickname
) {
    public static CreateMemberDto of(
            String personalId,
            String password,
            String email,
            String nickname
    ) {
        return new CreateMemberDto(
                personalId,
                password,
                email,
                nickname
        );
    }
}