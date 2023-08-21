package com.example.eventarchitecturetest.domain.member.dto;

import com.example.eventarchitecturetest.domain.member.entity.Member;
import com.example.eventarchitecturetest.domain.member.entity.MemberEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.UUID;

@Getter
public class MemberLoginEvent extends EventPayload {
    private Long id;
    private String email;
    private String password;

    public static MemberLoginEvent from(Member member) {
        MemberLoginEvent event = new MemberLoginEvent();
        event.eventId = UUID.randomUUID().toString();
        event.id = member.getId();
        event.email = member.getEmail();
        event.password = member.getPassword();
        return event;
    }

    public MemberEvent toEntity() throws JsonProcessingException {
        return MemberEvent.builder()
                .id(eventId)
                .memberId(id)
                .eventType(MemberEventType.LOGIN)
                .attributes(new ObjectMapper().writeValueAsString(this))
                .build();
    }
}
