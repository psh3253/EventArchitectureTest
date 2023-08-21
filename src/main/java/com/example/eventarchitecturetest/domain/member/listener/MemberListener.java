package com.example.eventarchitecturetest.domain.member.listener;

import com.example.eventarchitecturetest.domain.member.dto.EventPayload;
import com.example.eventarchitecturetest.domain.member.dto.MemberJoinEvent;
import com.example.eventarchitecturetest.domain.member.dto.MemberLoginEvent;
import com.example.eventarchitecturetest.domain.member.service.EventRecorder;
import com.example.eventarchitecturetest.domain.member.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.awspring.cloud.sns.core.SnsTemplate;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class MemberListener {
    private final SnsTemplate snsTemplate;
    private final EventRecorder eventRecorder;
    private final MemberService memberService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleMemberJoinEvent(MemberJoinEvent event) {
        snsTemplate.sendNotification("member-join", event, null);
    }

    @EventListener
    @Transactional
    public void handleEvent(MemberJoinEvent event) throws JsonProcessingException {
        eventRecorder.record(event.toEntity());
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleMemberLoginEvent(MemberLoginEvent event) {
        snsTemplate.sendNotification("member-login", event, null);
    }

    @EventListener
    @Transactional
    public void handleEvent(MemberLoginEvent event) throws JsonProcessingException {
        eventRecorder.record(event.toEntity());
    }

    @SqsListener(value = "join-send-welcome-email")
    public void sendWelcomeEmail(@Payload MemberJoinEvent payload) {
        memberService.sendWelcomeEmail(payload.getEmail(), payload.getNickname());
    }

    @SqsListener(value = "join-give-welcome-point")
    public void giveWelcomePoint(@Payload MemberJoinEvent payload) {
        memberService.giveWelcomePoint(payload.getId());
    }

    @SqsListener(value = "member-publish-record")
    public void recordEventPublish(@Payload EventPayload payload) {
        eventRecorder.record(payload.getEventId());
    }
}
