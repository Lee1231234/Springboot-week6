package com.example.springbootweek6.Service;

import com.example.springbootweek6.Dto.Request.LoginRequestDto;
import com.example.springbootweek6.Dto.Request.MemberRequestDto;
import com.example.springbootweek6.Dto.Response.MemberResponseDto;
import com.example.springbootweek6.Repository.MemberRepository;
import com.example.springbootweek6.domain.Member;
import com.example.springbootweek6.domain.TokenDto;
import com.example.springbootweek6.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {


    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    private final MemberRepository memberRepository;

    public ResponseEntity<?> createMember(MemberRequestDto requestDto) {
        if (null != isPresentMember(requestDto.getNickname())) {
            return  Request("회원가입 실패",false);
        }

        if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
            return Request("회원가입 실패",false);
        }

        Member member = Member.builder()
                .nickname(requestDto.getNickname())
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .build();
        memberRepository.save(member);
        return Request("회원가입 성공",true);
    }

    public ResponseEntity<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
        Member member = isPresentMember(requestDto.getNickname());
        if (null == member) {
            return Request("로그인 실패",false);
        }

        if (!member.validatePassword(passwordEncoder, requestDto.getPassword())) {
            return Request("로그인 실패",false);
        }


        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        tokenToHeaders(tokenDto, response);

        return Request("로그인 성공",true);
    }

    public ResponseEntity<?> logout(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return ResponseEntity.badRequest().body("Token이 유효하지 않습니다.");
        }
        Member member = tokenProvider.getMemberFromAuthentication();
        if (null == member) {
            return ResponseEntity.badRequest().body("사용자를 찾을 수 없습니다.");
        }

        return tokenProvider.deleteRefreshToken(member);
    }

    @Transactional(readOnly = true)
    public Member isPresentMember(String nickname) {
        Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
        return optionalMember.orElse(null);
    }

    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }
    public ResponseEntity<?> Request(String message,boolean bool){
        if(bool){
            return ResponseEntity.ok(
                    MemberResponseDto.builder()
                            .ok(bool)
                            .message(message)
                            .build()
            );
        }else{
            return ResponseEntity.badRequest().body(
                    MemberResponseDto.builder()
                            .ok(bool)
                            .message(message)
                            .build()
            );
        }

    }
}
