package com.example.springbootweek6.domain;

import com.example.springbootweek6.Dto.Request.CommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "post_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;


    @Column(nullable = false)
    private String content;


    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }


    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}