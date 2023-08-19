package site.board.boardtraining.domain.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.board.boardtraining.domain.board.dto.BoardDto;
import site.board.boardtraining.domain.board.dto.CreateBoardDto;
import site.board.boardtraining.domain.board.dto.DeleteBoardDto;
import site.board.boardtraining.domain.board.dto.UpdateBoardDto;
import site.board.boardtraining.domain.board.entity.Board;
import site.board.boardtraining.domain.board.repository.BoardRepository;
import site.board.boardtraining.domain.member.repository.MemberRepository;
import site.board.boardtraining.global.exception.ResourceNotFoundException;
import site.board.boardtraining.global.exception.UnauthorizedResourceAccessException;

import java.util.List;
import java.util.stream.Collectors;

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
    public BoardDto getBoard(Long boardId) {
        return BoardDto.from(
                boardRepository.findById(boardId)
                        .orElseThrow(() -> new ResourceNotFoundException(BOARD_NOT_FOUND))
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<BoardDto> getAllBoard() {
        return boardRepository.findAll()
                .stream()
                .map(BoardDto::from)
                .collect(Collectors.toList());
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