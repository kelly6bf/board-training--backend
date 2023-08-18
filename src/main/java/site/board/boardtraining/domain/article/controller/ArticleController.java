package site.board.boardtraining.domain.article.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.board.boardtraining.domain.article.dto.api.CreateArticleRequest;
import site.board.boardtraining.domain.article.dto.api.CreateArticleResponse;
import site.board.boardtraining.domain.article.dto.api.UpdateArticleRequest;
import site.board.boardtraining.domain.article.dto.business.ArticleDto;
import site.board.boardtraining.domain.article.dto.business.DeleteArticleDto;
import site.board.boardtraining.domain.article.service.ArticleService;
import site.board.boardtraining.domain.auth.data.CustomUserPrincipal;
import site.board.boardtraining.global.response.success.SingleSuccessApiResponse;
import site.board.boardtraining.global.response.success.SuccessApiResponse;

@RestController
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/api/articles/{article-id}")
    public ResponseEntity<SingleSuccessApiResponse<ArticleDto>> getArticle(
            @PathVariable("article-id") Long articleId
    ) {
        return ResponseEntity.ok(
                SingleSuccessApiResponse.of(
                        "성공적으로 게시글이 조회되었습니다.",
                        articleService.getArticle(articleId)
                )
        );
    }

    @PostMapping("/api/articles")
    public ResponseEntity<SingleSuccessApiResponse<CreateArticleResponse>> createArticle(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @RequestBody CreateArticleRequest request
    ) {
        return ResponseEntity.ok(
                SingleSuccessApiResponse.of(
                        "성공적으로 게시글이 생성되었습니다.",
                        CreateArticleResponse.of(
                                articleService.createArticle(
                                        request.toDto(principal.getUsername())
                                )
                        )
                )
        );
    }

    @PatchMapping("/api/articles/{article-id}")
    public ResponseEntity<SuccessApiResponse> updateArticle(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable("article-id") Long articleId,
            @RequestBody UpdateArticleRequest request
    ) {
        articleService.updateArticle(
                request.toDto(
                        articleId,
                        principal.getUsername()
                )
        );
        return ResponseEntity.ok(
                SuccessApiResponse.of(
                        "성공적으로 게시글이 수정되었습니다."
                )
        );
    }

    @DeleteMapping("/api/articles/{article-id}")
    public ResponseEntity<SuccessApiResponse> deleteArticle(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable("article-id") Long articleId
    ) {
        articleService.deleteArticle(
                DeleteArticleDto.of(
                        articleId,
                        principal.getUsername()
                )
        );
        return ResponseEntity.ok(
                SuccessApiResponse.of(
                        "성공적으로 게시글이 삭제되었습니다."
                )
        );
    }
}