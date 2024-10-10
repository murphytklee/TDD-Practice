package com.example.demo.app.membership.dto;

import com.example.demo.app.enums.MembershipType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class MembershipRequest {
    
    private final Integer point;
    private final MembershipType membershipType;
}
