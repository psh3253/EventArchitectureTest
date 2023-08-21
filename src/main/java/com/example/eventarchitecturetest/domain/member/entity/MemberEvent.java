package com.example.eventarchitecturetest.domain.member.entity;

import com.example.eventarchitecturetest.domain.member.dto.MemberEventType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class MemberEvent {
    @Id
    private String id;

    @Column(nullable = false)
    private boolean published;

    @Column
    private LocalDateTime publishedAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @Column
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberEventType eventType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String attributes;

    @Builder
    public MemberEvent(String id, Long memberId, MemberEventType eventType, String attributes) {
        this.id = id;
        this.memberId = memberId;
        this.eventType = eventType;
        this.attributes = attributes;
    }

    public void publish() {
        this.published = true;
        this.publishedAt = LocalDateTime.now();
    }
}
