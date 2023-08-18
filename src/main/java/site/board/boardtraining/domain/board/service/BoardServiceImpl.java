package site.board.boardtraining.domain.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.board.boardtraining.domain.board.dto.BoardDto;
import site.board.boardtraining.domain.board.dto.CreateBoardDto;
import site.board.boardtraining.domain.board.dto.DeleteBoardDto;
import site.board.boardtraining.domain.board.dto.UpdateBoardDto;
import site.board.boardtraining.domain.board.entity.Board;
import site.board.boardtraining.domain.board.repository.BoardRepository;
import site.board.boardtraining.domain.member.entity.Member;
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

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public BoardDto getBoard(Long boardId) {
        return BoardDto.from(
                boardRepository.findById(boardId)
                        .orElseThrow(() -> new ResourceNotFoundException(BOARD_NOT_FOUND))
        );
    }

    @Override
    public List<BoardDto> getAllBoard() {
        return boardRepository.findAll()
                .stream()
                .map(BoardDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public Long createBoard(CreateBoardDto dto) {
        return boardRepository.save(dto.toEntity()).getId();
    }

    @Override
    public void updateBoard(UpdateBoardDto dto) {
        Board savedBoard = boardRepository.getReferenceById(dto.boardId());

        verifyBoardOwner(
                savedBoard,
                dto.member()
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
                dto.member()
        );

        boardRepository.delete(savedBoard);
    }

    private void verifyBoardOwner(
            Board board,
            Member member
    ) {
        if (!board.getMember().equals(member))
            throw new UnauthorizedResourceAccessException();
    }
}