package com.example.eventarchitecturetest.domain.member.service;

import com.example.eventarchitecturetest.domain.exception.EventNotFoundException;
import com.example.eventarchitecturetest.domain.member.entity.MemberEvent;
import com.example.eventarchitecturetest.domain.member.repository.MemberEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EventRecorder {
    private final MemberEventRepository memberEventRepository;

    @Transactional
    public void record(MemberEvent memberEvent) {
        memberEventRepository.save(memberEvent);
    }

    @Transactional
    public void record(String eventId) {
        MemberEvent memberEvent = memberEventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("event not found"));
        memberEvent.publish();
    }
}
