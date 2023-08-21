package com.example.eventarchitecturetest.domain.member.controller;

import com.example.eventarchitecturetest.domain.member.dto.MemberJoinRequest;
import com.example.eventarchitecturetest.domain.member.dto.MemberLoginRequest;
import com.example.eventarchitecturetest.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@RestController
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody MemberJoinRequest request) {
        memberService.join(request);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberLoginRequest request) {
        memberService.login(request);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
