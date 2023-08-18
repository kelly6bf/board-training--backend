package site.board.boardtraining.domain.image.constant;

import lombok.Getter;

@Getter
public enum ImageType {
    PROFILE("/profile-image"),
    BOARD_THUMBNAIL("/board-thumbnail-image"),
    ARTICLE_CONTENT("/article-content-image"),
    ARTICLE_COMMENT_CONTENT("/article-comment-content-image");

    private final String path;

    ImageType(String path) {
        this.path = path;
    }
}