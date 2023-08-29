package site.board.boardtraining.domain.comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.article.repository.ArticleRepository;
import site.board.boardtraining.domain.comment.dto.business.*;
import site.board.boardtraining.domain.comment.entity.ArticleComment;
import site.board.boardtraining.domain.comment.repository.ArticleCommentRepository;
import site.board.boardtraining.domain.member.entity.Member;
import site.board.boardtraining.domain.member.repository.MemberRepository;
import site.board.boardtraining.global.exception.ResourceNotFoundException;
import site.board.boardtraining.global.exception.UnauthorizedResourceAccessException;

import java.util.List;
import java.util.stream.Collectors;

import static site.board.boardtraining.domain.article.exception.ArticleErrorCode.ARTICLE_NOT_FOUND;
import static site.board.boardtraining.domain.comment.constant.ArticleCommentType.PARENT;
import static site.board.boardtraining.domain.comment.exception.ArticleCommentErrorCode.ARTICLE_COMMENT_NOT_FOUND;
import static site.board.boardtraining.domain.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;

@Transactional
@Service
public class ArticleCommentServiceImpl
        implements ArticleCommentService {

    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleCommentReactionService articleCommentReactionService;

    public ArticleCommentServiceImpl(
            MemberRepository memberRepository,
            ArticleRepository articleRepository,
            ArticleCommentRepository articleCommentRepository,
            ArticleCommentReactionService articleCommentReactionService
    ) {
        this.memberRepository = memberRepository;
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.articleCommentReactionService = articleCommentReactionService;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ArticleCommentsDto> getArticleComments(
            Long articleId
    ) {
        Article article = getArticleEntity(articleId);

        return articleCommentRepository.findAllByArticleAndCommentType(article, PARENT)
                .stream()
                .map(parentComment -> ArticleCommentsDto.from(
                        parentComment,
                        articleCommentRepository.findAllByParentCommentId(parentComment.getId())
                                .stream()
                                .map(childComment ->
                                        ArticleCommentDto.from(
                                                childComment,
                                                articleCommentReactionService.getArticleCommentLikeCount(childComment),
                                                articleCommentReactionService.getArticleCommentDislikeCount(childComment)
                                        )
                                )
                                .collect(Collectors.toList()),
                        articleCommentReactionService.getArticleCommentLikeCount(parentComment),
                        articleCommentReactionService.getArticleCommentDislikeCount(parentComment)
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void createParentArticleComment(CreateArticleCommentDto dto) {
        articleCommentRepository.save(
                dto.toEntity(
                        getArticleEntity(dto.articleId()),
                        getMemberEntity(dto.memberId())
                )
        );
    }

    @Override
    public void createChildArticleComment(CreateArticleCommentDto dto) {
        ArticleComment parentComment = getArticleCommentEntity(dto.parentCommentId());

        articleCommentRepository.save(
                dto.toEntity(
                        parentComment.getArticle(),
                        getMemberEntity(dto.memberId())
                )
        );
    }

    @Override
    public void updateArticleComment(UpdateArticleCommentDto dto) {
        ArticleComment savedArticleComment = getArticleCommentEntity(dto.commentId());

        verifyArticleCommentOwner(
                savedArticleComment,
                dto.memberId()
        );

        savedArticleComment.update(dto.content());
    }

    @Override
    public void deleteArticleComment(DeleteArticleCommentDto dto) {
        ArticleComment savedArticleComment = getArticleCommentEntity(dto.commentId());

        verifyArticleCommentOwner(
                savedArticleComment,
                dto.memberId()
        );

        // 최상위 댓글일 경우 자식 댓글과 함께 DB 에서 제거
        if (savedArticleComment.getCommentType() == PARENT) {
            articleCommentRepository.deleteAllByParentCommentId(savedArticleComment.getId());
        }

        articleCommentRepository.delete(savedArticleComment);
    }

    private Member getMemberEntity(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException(MEMBER_NOT_FOUND));
    }

    private Article getArticleEntity(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException(ARTICLE_NOT_FOUND));
    }

    private ArticleComment getArticleCommentEntity(Long articleCommentId) {
        return articleCommentRepository.findById(articleCommentId)
                .orElseThrow(() -> new ResourceNotFoundException(ARTICLE_COMMENT_NOT_FOUND));
    }

    private void verifyArticleCommentOwner(
            ArticleComment articleComment,
            Long memberId
    ) {
        if (!articleComment.getMember().getId().equals(memberId))
            throw new UnauthorizedResourceAccessException();
    }
}