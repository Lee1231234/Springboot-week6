package com.example.springbootweek6.domain;

import com.example.springbootweek6.Dto.Request.PostRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long view;
    @Column(nullable = false)
    private String title;


    @Column(nullable = false)
    private String review;

    @Column(length = 1000)
    private String imgUrl;

    private int likes;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

/*    public Post(PostRequestDto requestDto,Member member) {
        this.title= requestDto.getTitle();
        this.review= requestDto.getReview();
        this.imgUrl= requestDto.getImageUrl();
        this.view =0L;
        this.member = member;
    }*/


    public void update(PostRequestDto requestDto, String imageUrl) {
        this.title = requestDto.getTitle();
        this.review = requestDto.getReview();
        this.imgUrl = imageUrl;
    }
    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }
    public void viewupdate(){
        this.view++;
    }
    public void likeupdate(boolean bool){
        if(bool){
            this.likes++;
        }else{
            this.likes--;
        }

    }


}
