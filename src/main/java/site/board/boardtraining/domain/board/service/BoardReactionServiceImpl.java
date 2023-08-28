package site.board.boardtraining.domain.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.board.boardtraining.domain.board.entity.Board;
import site.board.boardtraining.domain.board.entity.BoardReaction;
import site.board.boardtraining.domain.board.exception.BoardBusinessException;
import site.board.boardtraining.domain.board.repository.BoardReactionRepository;
import site.board.boardtraining.domain.board.repository.BoardRepository;
import site.board.boardtraining.domain.member.entity.Member;
import site.board.boardtraining.domain.member.repository.MemberRepository;
import site.board.boardtraining.global.exception.ResourceNotFoundException;
import site.board.boardtraining.global.exception.UnauthorizedResourceAccessException;

import java.util.Objects;

import static site.board.boardtraining.domain.board.constant.BoardReactionType.DISLIKE;
import static site.board.boardtraining.domain.board.constant.BoardReactionType.LIKE;
import static site.board.boardtraining.domain.board.exception.BoardErrorCode.*;
import static site.board.boardtraining.domain.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;

@Transactional
@Service
public class BoardReactionServiceImpl
        implements BoardReactionService {

    private final BoardRepository boardRepository;
    private final BoardReactionRepository boardReactionRepository;
    private final MemberRepository memberRepository;

    public BoardReactionServiceImpl(
            BoardRepository boardRepository,
            BoardReactionRepository boardReactionRepository,
            MemberRepository memberRepository
    ) {
        this.boardRepository = boardRepository;
        this.boardReactionRepository = boardReactionRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public int getBoardLikeCount(Board board) {
        return boardReactionRepository.countAllByTypeAndBoard(
                LIKE,
                board
        );
    }

    @Override
    public void addBoardLike(Long boardId, Long memberId) {

        Board board = getBoardEntity(boardId);
        Member member = getMemberEntity(memberId);

        if (checkReactionExistence(board, member))
            throw new BoardBusinessException(BOARD_REACTION_ALREADY_EXIST);

        boardReactionRepository.save(
                BoardReaction.of(
                        LIKE,
                        board,
                        getMemberEntity(memberId)
                )
        );
    }

    @Override
    public void deleteBoardLike(Long boardId, Long memberId) {

        Board board = getBoardEntity(boardId);
        Member member = getMemberEntity(memberId);

        BoardReaction boardReaction = boardReactionRepository.findByBoardAndMember(board, member)
                .orElseThrow(() -> new ResourceNotFoundException(BOARD_REACTION_NOT_FOUND));

        verifyReactionOwner(boardReaction, member);

        boardReactionRepository.delete(boardReaction);
    }

    @Transactional(readOnly = true)
    @Override
    public int getBoardDislikeCount(Board board) {
        return boardReactionRepository.countAllByTypeAndBoard(
                DISLIKE,
                board
        );
    }

    @Override
    public void addBoardDislike(Long boardId, Long memberId) {

        Board board = getBoardEntity(boardId);
        Member member = getMemberEntity(memberId);

        if (checkReactionExistence(board, member))
            throw new BoardBusinessException(BOARD_REACTION_ALREADY_EXIST);

        boardReactionRepository.save(
                BoardReaction.of(
                        DISLIKE,
                        board,
                        getMemberEntity(memberId)
                )
        );
    }

    @Override
    public void deleteBoardDislike(Long boardId, Long memberId) {

        Board board = getBoardEntity(boardId);
        Member member = getMemberEntity(memberId);

        BoardReaction boardReaction = boardReactionRepository.findByBoardAndMember(board, member)
                .orElseThrow(() -> new ResourceNotFoundException(BOARD_REACTION_NOT_FOUND));

        verifyReactionOwner(boardReaction, member);

        boardReactionRepository.delete(boardReaction);
    }

    private boolean checkReactionExistence(
            Board board,
            Member member
    ) {
        return boardReactionRepository.existsByBoardAndMember(
                board,
                member
        );
    }

    private void verifyReactionOwner(BoardReaction reaction, Member member) {
        if (!Objects.equals(reaction.getMember(), member))
            throw new UnauthorizedResourceAccessException();
    }

    @Transactional(readOnly = true)
    public Board getBoardEntity(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException(BOARD_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Member getMemberEntity(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException(MEMBER_NOT_FOUND));
    }
}