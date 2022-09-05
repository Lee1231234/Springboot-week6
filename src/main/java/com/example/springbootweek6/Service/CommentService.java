package com.example.springbootweek6.Service;

import com.example.springbootweek6.Dto.Request.CommentRequestDto;
import com.example.springbootweek6.Dto.Response.CommentResponseDto;
import com.example.springbootweek6.Dto.Response.ResponseDto;
import com.example.springbootweek6.Repository.CommentRepository;
import com.example.springbootweek6.domain.Comment;
import com.example.springbootweek6.domain.Member;
import com.example.springbootweek6.domain.Post;
import com.example.springbootweek6.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostService postService;
    private final CommentRepository commentRepository;
    private final TokenProvider tokenProvider;



    //사용하지 않음.
    public ResponseEntity<?> getAllCommentsByPost(Long id) {
        return null;
    }
    @Transactional
    public ResponseEntity<?> createComment(CommentRequestDto requestDto, HttpServletRequest request) {
        Member member = validateMember(request);
        Post post = postService.isPresentPost(requestDto.getPostid());

        ResponseEntity<?> check =checkError(post, member);
        if(check!=null){
            ResponseEntity.badRequest().body(check);
        }
        Comment comment = Comment.builder()
                .member(member)
                .post(post)
                .content(requestDto.getContent())
                .build();
        commentRepository.save(comment);
        return ResponseEntity.ok(ResponseDto.success(
                CommentResponseDto.builder()
                        .id(comment.getId())
                        .author(comment.getMember().getNickname())
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .modifiedAt(comment.getModifiedAt())
                        .build()
        ));
    }
    @Transactional
    public ResponseEntity<?> updateComment(Long id, CommentRequestDto requestDto, HttpServletRequest request) {
        Member member = validateMember(request);
        Post post = postService.isPresentPost(requestDto.getPostid());
        Comment comment = isPresentComment(id);
        ResponseEntity<?> check =checkError(post, member,comment);
        if(check!=null){
            ResponseEntity.badRequest().body(check);
        }
        comment.update(requestDto);
        return ResponseEntity.ok(ResponseDto.success(
                CommentResponseDto.builder()
                        .id(comment.getId())
                        .author(comment.getMember().getNickname())
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .modifiedAt(comment.getModifiedAt())
                        .build()
        ));
    }
    @Transactional(readOnly = true)
    public Comment isPresentComment(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        return optionalComment.orElse(null);
    }

    @Transactional
    public ResponseEntity<?> deleteComment(Long id, HttpServletRequest request) {
        Member member = validateMember(request);
        Comment comment = isPresentComment(id);
        ResponseEntity<?> check =checkError( member,comment);
        if(check!=null){
            ResponseEntity.badRequest().body(check);
        }
        commentRepository.delete(comment);

        return ResponseEntity.ok(ResponseDto.success("success"));

    }

    //사용하지않음
    public ResponseEntity<?> createcommentlikes(Long id, HttpServletRequest request) {
        return null;

    }
    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

    @Transactional
    public ResponseEntity<?> checkError(Post post,Member member,Comment comment){
        if (null == member) {
            return  ResponseEntity.badRequest().body(ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다."));
        }


        if (null == post) {
            return  ResponseEntity.badRequest().body(ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다."));
        }

        if (null == comment) {
            return  ResponseEntity.badRequest().body(ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다."));
        }

        if (comment.validateMember(member)) {
            return  ResponseEntity.badRequest().body(ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다."));
        }
        return null;
    }
    @Transactional
    public ResponseEntity<?> checkError(Post post,Member member){
        if (null == member) {
            return  ResponseEntity.badRequest().body(ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다."));
        }


        if (null == post) {
            return  ResponseEntity.badRequest().body(ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다."));
        }
        return null;
    }
    @Transactional
    public ResponseEntity<?> checkError(Member member,Comment comment){
        if (null == member) {
            return  ResponseEntity.badRequest().body(ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다."));
        }

        if (null == comment) {
            return  ResponseEntity.badRequest().body(ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다."));
        }

        if (comment.validateMember(member)) {
            return  ResponseEntity.badRequest().body(ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다."));
        }
        return null;
    }
}
