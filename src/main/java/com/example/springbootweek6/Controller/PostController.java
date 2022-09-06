package com.example.springbootweek6.Controller;



import com.example.springbootweek6.Dto.Request.PostRequestDto;
import com.example.springbootweek6.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @RequestMapping(value = "/api/auth/post", method = RequestMethod.POST)
    public ResponseEntity<?> createPost(@RequestBody PostRequestDto requestDto,
                                        HttpServletRequest request) {
        return postService.createPost(requestDto, request);
    }

    @RequestMapping(value = "/api/post/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @RequestMapping(value = "/api/main", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPosts() {
        return postService.getAllPost();
    }

    @RequestMapping(value = "/api/like", method = RequestMethod.GET)
    public ResponseEntity<?> getAllLikePosts() {
        return postService.getAllLikePost();
    }

    @RequestMapping(value = "/api/auth/post/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto,
                                     HttpServletRequest request) {
        return postService.updatePost(id, postRequestDto, request);
    }

    @RequestMapping(value = "/api/auth/post/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePost(@PathVariable Long id,
                                     HttpServletRequest request) {
        return postService.deletePost(id, request);
    }
    //일단 라이크용
    @RequestMapping(value = "/api/auth/post/likes/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> createpostlikes(@PathVariable Long id,
                                          HttpServletRequest request){
        return postService.createpostlikes(id,request);
    }

}
