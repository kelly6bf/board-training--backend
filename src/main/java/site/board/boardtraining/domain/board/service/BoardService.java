package site.board.boardtraining.domain.board.service;

import org.springframework.data.domain.Page;
import site.board.boardtraining.domain.board.dto.business.*;

public interface BoardService {

    Page<BoardDto> searchBoards(SearchBoardsDto dto);

    BoardDto getBoard(Long boardId);

    Long createBoard(CreateBoardDto dto);

    void updateBoard(UpdateBoardDto dto);

    void deleteBoard(DeleteBoardDto dto);
}