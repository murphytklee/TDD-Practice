package com.example.demo.app.membership.controller;

import static com.example.demo.app.membership.constants.MembershipConstants.USER_ID_HEADER;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.app.membership.dto.MembershipAddResponse;
import com.example.demo.app.membership.dto.MembershipDetailResponse;
import com.example.demo.app.membership.dto.MembershipRequest;
import com.example.demo.app.membership.service.MembershipService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;
    
    @PostMapping("/api/v1/memberships")
    public ResponseEntity<MembershipAddResponse> addMembership(@RequestHeader(USER_ID_HEADER) final String userId, @RequestBody @Valid final MembershipRequest membershipRequest) {
        membershipService.addMembership(userId, membershipRequest.getMembershipType(), membershipRequest.getPoint());
        
        final MembershipAddResponse membershipResponse = membershipService.addMembership(userId, membershipRequest.getMembershipType(), membershipRequest.getPoint());

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(membershipResponse);
    }
    
    @GetMapping("/api/v1/memberships")
    public ResponseEntity<List<MembershipDetailResponse>> getMembershipList(@RequestHeader(USER_ID_HEADER) final String userId) {
        return ResponseEntity.ok(membershipService.getMembershipList(userId));
    }
    
    @GetMapping("/api/v1/memberships/{id}")
    public ResponseEntity<MembershipDetailResponse> getMembership(@RequestHeader(USER_ID_HEADER) final String userId, @PathVariable final Long id) {
        return ResponseEntity.ok(membershipService.getMembership(id, userId));
    }
}
