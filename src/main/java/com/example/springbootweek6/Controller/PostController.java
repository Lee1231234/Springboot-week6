package com.example.springbootweek6.Controller;



import com.example.springbootweek6.Dto.Request.PostRequestDto;
import com.example.springbootweek6.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping(value = "/api/auth/post")
    public ResponseEntity<?> createPost(PostRequestDto requestDto,
                                        @RequestPart (value="image",required = false) MultipartFile image, HttpServletRequest request) throws IOException {

        return postService.createPost(requestDto, request, image);
    }

    @GetMapping(value = "/api/post/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @GetMapping(value = "/api/main")
    public ResponseEntity<?> getAllPosts() {
        return postService.getAllPost();
    }

    @GetMapping(value = "/api/like")
    public ResponseEntity<?> getAllLikePosts() {
        return postService.getAllLikePost();
    }

    @PutMapping(value = "/api/auth/post/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto,
                                     HttpServletRequest request,
                                        @RequestPart (value="image") MultipartFile imgae) throws IOException{
        return postService.updatePost(id, postRequestDto, imgae, request);
    }

    @DeleteMapping(value = "/api/auth/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id,
                                     HttpServletRequest request) {
        return postService.deletePost(id, request);
    }
    //일단 라이크용
    @GetMapping(value = "/api/auth/post/likes/{id}")
    public ResponseEntity<?> createpostlikes(@PathVariable Long id,
                                          HttpServletRequest request){
        return postService.createpostlikes(id,request);
    }

}
