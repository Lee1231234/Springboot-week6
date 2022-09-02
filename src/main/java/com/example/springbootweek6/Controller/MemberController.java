package com.example.springbootweek6.Controller;

import com.example.springbootweek6.Dto.Request.LoginRequestDto;
import com.example.springbootweek6.Dto.Request.MemberRequestDto;
import com.example.springbootweek6.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController

public class MemberController {

    private final MemberService memberService;

    @PostMapping(value = "api/member/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid MemberRequestDto requestDto){
     return memberService.createMember(requestDto);
    }

    @PostMapping(value = "api/member/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto requestDto, HttpServletResponse response){
        return memberService.login(requestDto, response);
    }

    @PostMapping(value ="/api/auth/member/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return memberService.logout(request);
    }



}