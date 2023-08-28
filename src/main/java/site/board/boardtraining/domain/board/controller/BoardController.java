package site.board.boardtraining.domain.board.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.board.boardtraining.domain.auth.data.CustomUserPrincipal;
import site.board.boardtraining.domain.board.dto.api.CreateBoardRequest;
import site.board.boardtraining.domain.board.dto.api.CreateBoardResponse;
import site.board.boardtraining.domain.board.dto.api.UpdateBoardRequest;
import site.board.boardtraining.domain.board.dto.business.BoardDto;
import site.board.boardtraining.domain.board.dto.business.DeleteBoardDto;
import site.board.boardtraining.domain.board.dto.business.SearchBoardsDto;
import site.board.boardtraining.domain.board.service.BoardService;
import site.board.boardtraining.domain.board.service.BoardReactionService;
import site.board.boardtraining.global.response.success.MultipleSuccessApiResponse;
import site.board.boardtraining.global.response.success.SingleSuccessApiResponse;
import site.board.boardtraining.global.response.success.SuccessApiResponse;

@RequestMapping("/api/boards")
@RestController
public class BoardController {

    private final BoardService boardService;
    private final BoardReactionService boardReactionService;

    public BoardController(
            BoardService boardService,
            BoardReactionService boardReactionService
    ) {
        this.boardService = boardService;
        this.boardReactionService = boardReactionService;
    }

    @GetMapping
    public ResponseEntity<MultipleSuccessApiResponse<BoardDto>> searchBoards(
            @RequestParam(required = false) String searchKeyword,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(
                MultipleSuccessApiResponse.of(
                        "성공적으로 게시판이 조회되었습니다.",
                        boardService.searchBoards(
                                SearchBoardsDto.of(
                                        pageable,
                                        searchKeyword
                                )
                        ).getContent()
                )
        );
    }

    @GetMapping("/{board-id}")
    public ResponseEntity<SingleSuccessApiResponse<BoardDto>> getBoard(
            @PathVariable("board-id") Long boardId
    ) {
        return ResponseEntity.ok(
                SingleSuccessApiResponse.of(
                        "성공적으로 게시판이 조회되었습니다.",
                        boardService.getBoard(boardId)
                )
        );
    }

    @PostMapping
    public ResponseEntity<SingleSuccessApiResponse<CreateBoardResponse>> createBoard(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @RequestBody CreateBoardRequest request
    ) {
        return ResponseEntity.ok(
                SingleSuccessApiResponse.of(
                        "성공적으로 게시판이 생성되었습니다.",
                        CreateBoardResponse.of(
                                boardService.createBoard(
                                        request.toDto(principal.getMemberId())
                                )
                        )
                )
        );
    }

    @PatchMapping("/{board-id}")
    public ResponseEntity<SuccessApiResponse> updateBoard(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable("board-id") Long boardId,
            @RequestBody UpdateBoardRequest request
    ) {
        boardService.updateBoard(
                request.toDto(
                        boardId,
                        principal.getMemberId()
                )
        );

        return ResponseEntity.ok(
                SuccessApiResponse.of(
                        "성공적으로 게시판이 수정되었습니다."
                )
        );
    }

    @DeleteMapping("/{board-id}")
    public ResponseEntity<SuccessApiResponse> deleteBoard(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable("board-id") Long boardId
    ) {
        boardService.deleteBoard(
                DeleteBoardDto.of(
                        boardId,
                        principal.getMemberId()
                )
        );

        return ResponseEntity.ok(
                SuccessApiResponse.of(
                        "성공적으로 게시판이 삭제되었습니다."
                )
        );
    }

    @PostMapping("/{board-id}/like")
    public ResponseEntity<SuccessApiResponse> addBoardLike(
            @PathVariable("board-id") Long boardId,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        boardReactionService.addBoardLike(boardId, principal.getMemberId());
        return ResponseEntity.ok(
                SuccessApiResponse.of(
                        "성공적으로 게시판 좋아요가 등록되었습니다."
                )
        );
    }

    @DeleteMapping("/{board-id}/like")
    public ResponseEntity<SuccessApiResponse> deleteBoardLike(
            @PathVariable("board-id") Long boardId,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        boardReactionService.deleteBoardLike(boardId, principal.getMemberId());
        return ResponseEntity.ok(
                SuccessApiResponse.of(
                        "성공적으로 게시판 좋아요가 삭제되었습니다."
                )
        );
    }

    @PostMapping("/{board-id}/dislike")
    public ResponseEntity<SuccessApiResponse> addBoardDislike(
            @PathVariable("board-id") Long boardId,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        boardReactionService.addBoardDislike(boardId, principal.getMemberId());
        return ResponseEntity.ok(
                SuccessApiResponse.of(
                        "성공적으로 게시판 싫어요가 등록되었습니다."
                )
        );
    }

    @DeleteMapping("/{board-id}/dislike")
    public ResponseEntity<SuccessApiResponse> deleteBoardDislike(
            @PathVariable("board-id") Long boardId,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        boardReactionService.deleteBoardDislike(boardId, principal.getMemberId());
        return ResponseEntity.ok(
                SuccessApiResponse.of(
                        "성공적으로 게시판 싫어요가 삭제되었습니다."
                )
        );
    }
}