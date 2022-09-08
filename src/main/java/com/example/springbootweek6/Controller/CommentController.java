package com.example.springbootweek6.Controller;

import com.example.springbootweek6.Dto.Request.CommentRequestDto;

import com.example.springbootweek6.Service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Validated
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping(value = "/api/auth/comment")
    public ResponseEntity<?> createComment(@RequestBody CommentRequestDto requestDto,
                                           HttpServletRequest request) {
        return commentService.createComment(requestDto, request);
    }

    //사용하지않음.
    @GetMapping(value = "/api/comment/{id}")
    public ResponseEntity<?> getAllComments(@PathVariable Long id) {
        return commentService.getAllCommentsByPost(id);
    }

    @PutMapping(value = "/api/auth/comment/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto,
                                        HttpServletRequest request) {
        return commentService.updateComment(id, requestDto, request);
    }

    @DeleteMapping(value = "/api/auth/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id,
                                        HttpServletRequest request) {
        return commentService.deleteComment(id, request);
    }

    @PostMapping(value = "/api/auth/comment/likes/{id}")
    public ResponseEntity<?> createcommentlikes(@PathVariable Long id,
                                             HttpServletRequest request){
        return commentService.createcommentlikes(id,request);
    }
}