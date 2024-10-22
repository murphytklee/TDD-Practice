package com.example.demo.app.membership.dto;

import java.time.LocalDateTime;

import com.example.demo.app.enums.MembershipType;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class MembershipDetailResponse {
    
    private final Long id;
    private final MembershipType membershipType;
    private final LocalDateTime createdAt;
    private final Integer point;
}
