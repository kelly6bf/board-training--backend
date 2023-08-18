package site.board.boardtraining.domain.board.service;

import site.board.boardtraining.domain.board.dto.BoardDto;
import site.board.boardtraining.domain.board.dto.CreateBoardDto;
import site.board.boardtraining.domain.board.dto.DeleteBoardDto;
import site.board.boardtraining.domain.board.dto.UpdateBoardDto;

import java.util.List;

public interface BoardService {

    BoardDto getBoard(Long boardId);

    List<BoardDto> getAllBoard();

    Long createBoard(CreateBoardDto dto);

    void updateBoard(UpdateBoardDto dto);

    void deleteBoard(DeleteBoardDto dto);
}