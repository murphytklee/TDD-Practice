package com.example.demo.app.membership.dto;

import com.example.demo.app.enums.MembershipType;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class MembershipResponse {
    
    private final Long id;
    private final MembershipType membershipType;

}
