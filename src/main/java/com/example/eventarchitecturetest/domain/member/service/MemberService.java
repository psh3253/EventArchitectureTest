package com.example.eventarchitecturetest.domain.member.service;

import com.example.eventarchitecturetest.domain.member.dto.MemberJoinEvent;
import com.example.eventarchitecturetest.domain.member.dto.MemberJoinRequest;
import com.example.eventarchitecturetest.domain.member.dto.MemberLoginEvent;
import com.example.eventarchitecturetest.domain.member.dto.MemberLoginRequest;
import com.example.eventarchitecturetest.domain.member.entity.Member;
import com.example.eventarchitecturetest.domain.member.exception.MemberNotFoundException;
import com.example.eventarchitecturetest.domain.member.exception.PasswordMismatchException;
import com.example.eventarchitecturetest.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    @Value("${ses.sender.email}")
    private String senderEmail;

    private final MemberRepository memberRepository;
    private final MailSender mailSender;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void join(MemberJoinRequest request) {
        Member savedMember = memberRepository.save(request.toEntity());
        applicationEventPublisher.publishEvent(MemberJoinEvent.from(savedMember));
    }

    @Transactional
    public void login(MemberLoginRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(() -> new MemberNotFoundException("member not found"));
        if(!member.getPassword().equals(request.getPassword())) {
            throw new PasswordMismatchException("password mismatch");
        }
        applicationEventPublisher.publishEvent(MemberLoginEvent.from(member));
    }

    public void sendWelcomeEmail(String email, String nickname) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(email);
        message.setSubject("환영합니다.");
        message.setText(nickname + "님 환영합니다.");
        mailSender.send(message);
    }

    @Transactional
    public void giveWelcomePoint(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("member not found"));
        member.addPoint(1000);
    }
}
