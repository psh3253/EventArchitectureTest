package com.example.eventarchitecturetest.domain.member.repository;

import com.example.eventarchitecturetest.domain.member.entity.MemberEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberEventRepository extends JpaRepository<MemberEvent, String> {
}
