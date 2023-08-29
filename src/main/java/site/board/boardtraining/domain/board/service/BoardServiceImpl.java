package site.board.boardtraining.domain.board.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.board.boardtraining.domain.board.dto.business.*;
import site.board.boardtraining.domain.board.entity.Board;
import site.board.boardtraining.domain.board.repository.BoardRepository;
import site.board.boardtraining.domain.hashtag.service.HashtagService;
import site.board.boardtraining.domain.member.entity.Member;
import site.board.boardtraining.domain.member.repository.MemberRepository;
import site.board.boardtraining.global.exception.ResourceNotFoundException;
import site.board.boardtraining.global.exception.UnauthorizedResourceAccessException;

import static site.board.boardtraining.domain.board.exception.BoardErrorCode.BOARD_NOT_FOUND;
import static site.board.boardtraining.domain.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;

@Transactional
@Service
public class BoardServiceImpl
        implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final HashtagService hashtagService;
    private final BoardReactionService boardReactionService;

    public BoardServiceImpl(
            BoardRepository boardRepository,
            MemberRepository memberRepository,
            HashtagService hashtagService,
            BoardReactionService boardReactionService
    ) {
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
        this.hashtagService = hashtagService;
        this.boardReactionService = boardReactionService;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BoardDto> searchBoards(SearchBoardsDto dto) {
        if (dto.searchKeyword() == null || dto.searchKeyword().isBlank()) {
            return boardRepository.findAll(dto.pageable())
                    .map(board ->
                            BoardDto.from(
                                    board,
                                    hashtagService.getAllBoardHashtags(board),
                                    boardReactionService.getBoardLikeCount(board),
                                    boardReactionService.getBoardDislikeCount(board)
                            )
                    );
        }

        return boardRepository.findByTitleContaining(
                        dto.searchKeyword(),
                        dto.pageable()
                )
                .map(board ->
                        BoardDto.from(
                                board,
                                hashtagService.getAllBoardHashtags(board),
                                boardReactionService.getBoardLikeCount(board),
                                boardReactionService.getBoardDislikeCount(board)
                        )
                );
    }

    @Transactional(readOnly = true)
    @Override
    public BoardDto getBoard(Long boardId) {
        Board board = getBoardEntity(boardId);

        return BoardDto.from(
                board,
                hashtagService.getAllBoardHashtags(board),
                boardReactionService.getBoardLikeCount(board),
                boardReactionService.getBoardDislikeCount(board)
        );
    }

    @Override
    public Long createBoard(CreateBoardDto dto) {
        Board createdBoard = boardRepository.save(
                dto.toEntity(
                        getMemberEntity(dto.memberId())
                )
        );

        hashtagService.addBoardHashtags(
                dto.hashtags(),
                createdBoard
        );

        return createdBoard.getId();
    }

    @Override
    public void updateBoard(UpdateBoardDto dto) {
        Board savedBoard = getBoardEntity(dto.boardId());

        verifyBoardOwner(
                savedBoard,
                dto.memberId()
        );

        savedBoard.update(
                dto.title(),
                dto.description(),
                dto.thumbnailImageUrl()
        );

        hashtagService.updateBoardHashtags(
                dto.hashtags(),
                savedBoard
        );
    }

    @Override
    public void deleteBoard(DeleteBoardDto dto) {
        Board savedBoard = getBoardEntity(dto.boardId());

        verifyBoardOwner(
                savedBoard,
                dto.memberId()
        );

        hashtagService.deleteBoardHashtags(savedBoard);

        boardRepository.delete(savedBoard);
    }

    private Member getMemberEntity(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException(MEMBER_NOT_FOUND));
    }

    private Board getBoardEntity(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException(BOARD_NOT_FOUND));
    }

    private void verifyBoardOwner(
            Board board,
            Long memberId
    ) {
        if (!board.getMember().getId().equals(memberId))
            throw new UnauthorizedResourceAccessException();
    }
}