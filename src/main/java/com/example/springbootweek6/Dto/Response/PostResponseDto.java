package com.example.springbootweek6.Dto.Response;

import com.example.springbootweek6.domain.Comment;
import com.example.springbootweek6.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostResponseDto {

    private Long id;
    private String title;
    private String review;
    private String imgUrl;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private List<Comment> comments;
    public PostResponseDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.author = post.getMember().getNickname();
        this.imgUrl = post.getImgUrl();
        this.review = post.getReview();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }

}
