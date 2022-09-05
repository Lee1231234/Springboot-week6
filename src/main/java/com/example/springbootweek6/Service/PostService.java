package com.example.springbootweek6.Service;

import com.example.springbootweek6.Dto.Request.PostRequestDto;

import com.example.springbootweek6.Dto.Response.PostResponseDto;
import com.example.springbootweek6.Dto.Response.ResponseDto;
import com.example.springbootweek6.Dto.Response.ResponseErrorDto;
import com.example.springbootweek6.Repository.CommentRepository;
import com.example.springbootweek6.Repository.PostRepository;
import com.example.springbootweek6.Utill.S3Uploader;
import com.example.springbootweek6.domain.Member;
import com.example.springbootweek6.domain.Post;
import com.example.springbootweek6.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final S3Uploader s3Uploader;

    private final TokenProvider tokenProvider;
    @Transactional
    public ResponseEntity<?> createPost(PostRequestDto requestDto, HttpServletRequest request, MultipartFile image) throws IOException {

        Member member = validateMember(request);
        if (null == member) {
            return ResponseEntity.badRequest().body(new ResponseErrorDto("INVALID_TOKEN", "Token이 유효하지 않습니다."));
        }
        //이미지 postImage에 저장 폴더명 "static"
        String postImage = s3Uploader.upload(image, "static");


        Post post = Post.builder()
                .title(requestDto.getTitle())
                .review(requestDto.getReview())
                .member(member)
                .imgUrl(postImage)
                .build();

        postRepository.save(post);

        return ResponseEntity.ok(
                PostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .review(post.getReview())
                        .author(post.getMember().getNickname())
                        .imgUrl(post.getImgUrl())
                        .createdAt(post.getCreatedAt())
                        .modifiedAt(post.getModifiedAt())
                        .build()
        );

    }
    @Transactional
    public ResponseEntity<?> getPost(Long id) {
        Post post = isPresentPost(id);
        if (null == post) {
            return  ResponseEntity.badRequest().body(new ResponseErrorDto("NOT_FOUND", "존재하지 않는 게시글 id 입니다."));
        }
        return ResponseEntity.ok(ResponseDto.success(new PostResponseDto(post)
        ));
    }



    public ResponseEntity<?> getAllPost() {
        List<Post> posts=  postRepository.findAll();

        return  ResponseEntity.ok(ResponseDto.success(posts));
    }

    public ResponseEntity<?> updatePost(Long id, PostRequestDto requestDto, MultipartFile file, HttpServletRequest request) throws IOException{
        Member member = validateMember(request);
        Post post = isPresentPost(id);
        ResponseEntity<?> check =  CheckErrorPost(post,member);
        if(check!=null){
            return check;
        }

        String imageUrl = post.getImgUrl();
        //현재 이미지가 있다면
        if(imageUrl!= null) {
            //삭제할 주소값을 얻어서
            String deleteUrl = imageUrl.substring(imageUrl.indexOf("static"));
            //s3uploader에서 삭제해준다.
            s3Uploader.deleteImage(deleteUrl);
            imageUrl = s3Uploader.upload(file,"static");

        }
        post.update(requestDto, imageUrl);
        return ResponseEntity.ok(ResponseDto.success(new PostResponseDto(post)));

    }

    public ResponseEntity<?> deletePost(Long id, HttpServletRequest request) {
        Member member = validateMember(request);
        Post post = isPresentPost(id);

        ResponseEntity<?> check =  CheckErrorPost(post,member);
        if(check!=null){
            return check;
        }
        //파일명 찾아서 삭제
        String imageUrl = post.getImgUrl();
        String deleteUrl = imageUrl.substring(imageUrl.indexOf("static")); //이미지
        //s3에서 이미지 삭제
        s3Uploader.deleteImage(deleteUrl);
        postRepository.delete(post);
        return ResponseEntity.ok(ResponseDto.success(("Delete Success")));
    }

    public ResponseEntity<?> createpostlikes(Long id, HttpServletRequest request) {
        return null;
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
    @Transactional(readOnly = true)
    public Post isPresentPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }
    public ResponseEntity<?>  CheckErrorPost(Post post,Member member){
        if (null == member) {
            return ResponseEntity.badRequest().body(ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다."));
        }
        if (null == post) {
            return ResponseEntity.badRequest().body(ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다."));
        }
        if (post.validateMember(member)) {
            return ResponseEntity.badRequest().body(ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다."));
        }
        return null;
    }
}
