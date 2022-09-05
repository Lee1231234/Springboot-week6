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

    @RequestMapping(value = "/api/auth/comment", method = RequestMethod.POST)
    public ResponseEntity<?> createComment(@RequestBody CommentRequestDto requestDto,
                                           HttpServletRequest request) {
        return commentService.createComment(requestDto, request);
    }

    //사용하지않음.
    @RequestMapping(value = "/api/comment/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllComments(@PathVariable Long id) {
        return commentService.getAllCommentsByPost(id);
    }

    @RequestMapping(value = "/api/auth/comment/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto,
                                        HttpServletRequest request) {
        return commentService.updateComment(id, requestDto, request);
    }

    @RequestMapping(value = "/api/auth/comment/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteComment(@PathVariable Long id,
                                        HttpServletRequest request) {
        return commentService.deleteComment(id, request);
    }

    @RequestMapping(value = "/api/auth/comment/likes/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> createcommentlikes(@PathVariable Long id,
                                             HttpServletRequest request){
        return commentService.createcommentlikes(id,request);
    }
}