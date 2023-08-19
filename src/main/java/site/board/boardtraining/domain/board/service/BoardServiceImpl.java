package site.board.boardtraining.domain.board.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.board.boardtraining.domain.board.dto.business.*;
import site.board.boardtraining.domain.board.entity.Board;
import site.board.boardtraining.domain.board.repository.BoardRepository;
import site.board.boardtraining.domain.member.repository.MemberRepository;
import site.board.boardtraining.global.exception.ResourceNotFoundException;
import site.board.boardtraining.global.exception.UnauthorizedResourceAccessException;

import static site.board.boardtraining.domain.board.exception.BoardErrorCode.BOARD_NOT_FOUND;

@Transactional
@Service
public class BoardServiceImpl
        implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public BoardServiceImpl(
            BoardRepository boardRepository,
            MemberRepository memberRepository
    ) {
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BoardDto> searchBoards(SearchBoardsDto dto) {
        if (dto.searchKeyword() == null || dto.searchKeyword().isBlank()) {
            return boardRepository.findAll(dto.pageable())
                    .map(BoardDto::from);
        }

        return boardRepository.findByTitleContaining(
                        dto.searchKeyword(),
                        dto.pageable()
                )
                .map(BoardDto::from);
    }

    @Transactional(readOnly = true)
    @Override
    public BoardDto getBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .map(BoardDto::from)
                .orElseThrow(() -> new ResourceNotFoundException(BOARD_NOT_FOUND));
    }

    @Override
    public Long createBoard(CreateBoardDto dto) {
        Board createdBoard = boardRepository.save(
                dto.toEntity(
                        memberRepository.getReferenceById(dto.memberId())
                )
        );
        return createdBoard.getId();
    }

    @Override
    public void updateBoard(UpdateBoardDto dto) {
        Board savedBoard = boardRepository.getReferenceById(dto.boardId());

        verifyBoardOwner(
                savedBoard,
                dto.memberId()
        );

        savedBoard.update(
                dto.title(),
                dto.description(),
                dto.thumbnailImageUrl()
        );
    }

    @Override
    public void deleteBoard(DeleteBoardDto dto) {
        Board savedBoard = boardRepository.getReferenceById(dto.boardId());

        verifyBoardOwner(
                savedBoard,
                dto.memberId()
        );

        boardRepository.delete(savedBoard);
    }

    private void verifyBoardOwner(
            Board board,
            Long memberId
    ) {
        if (!board.getMember().getId().equals(memberId))
            throw new UnauthorizedResourceAccessException();
    }
}