package com.example.eventarchitecturetest.domain.member.dto;

import com.example.eventarchitecturetest.domain.member.entity.Member;
import com.example.eventarchitecturetest.domain.member.entity.MemberEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.UUID;

@Getter
public class MemberJoinEvent extends EventPayload{
    private Long id;
    private String email;
    private String password;
    private String nickname;

    public static MemberJoinEvent from(Member member) {
        MemberJoinEvent memberJoinEvent = new MemberJoinEvent();
        memberJoinEvent.eventId = UUID.randomUUID().toString();
        memberJoinEvent.id = member.getId();
        memberJoinEvent.email = member.getEmail();
        memberJoinEvent.password = member.getPassword();
        memberJoinEvent.nickname = member.getNickname();
        return memberJoinEvent;
    }

    public MemberEvent toEntity() throws JsonProcessingException {
        return MemberEvent.builder()
                .id(eventId)
                .memberId(id)
                .eventType(MemberEventType.JOIN)
                .attributes(new ObjectMapper().writeValueAsString(this))
                .build();
    }
}
