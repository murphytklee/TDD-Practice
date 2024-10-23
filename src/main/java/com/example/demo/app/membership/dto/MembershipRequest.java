package com.example.demo.app.membership.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.example.demo.app.enums.MembershipType;
import com.example.demo.app.membership.validation.ValidationGroups.MembershipAccumulateMarker;
import com.example.demo.app.membership.validation.ValidationGroups.MembershipAddMarker;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class MembershipRequest {
    
    @NotNull(groups = {MembershipAddMarker.class, MembershipAccumulateMarker.class})
    @Min(value = 0, groups = {MembershipAddMarker.class, MembershipAccumulateMarker.class})
    private final Integer point;

    @NotNull(groups = {MembershipAddMarker.class})
    private final MembershipType membershipType;
}
