package com.example.demo.app.membership.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.app.enums.MembershipType;
import com.example.demo.app.membership.entity.Membership;

@DataJpaTest
public class MembershipRepositoryTest {
    
    @Autowired
    private MembershipRepository membershipRepository;

    @Test
    public void 멤버십등록() {
        // given
        final Membership membership = Membership.builder()
            .userId("userId")
            .membershipType(MembershipType.NAVER)
            .point(10000)
            .build();

        // when 
        final Membership result = membershipRepository.save(membership);
        
        // then
        assertNotNull(result.getId());
        assertEquals("userId", result.getUserId());
        assertEquals(MembershipType.NAVER, result.getMembershipType());
        assertEquals(10000, result.getPoint());
    }

    @Test
    public void 멤버십존재체크() {
        // given
        final Membership membership = Membership.builder()
            .userId("userId")
            .membershipType(MembershipType.NAVER)
            .point(10000)
            .build();

        // when
        membershipRepository.save(membership);
        final Membership findResult = membershipRepository.findByUserIdAndMembershipType("userId", MembershipType.NAVER);

        // then
        assertNotNull(findResult);
        assertNotNull(findResult.getId());
        assertEquals("userId", findResult.getUserId());
        assertEquals(MembershipType.NAVER, findResult.getMembershipType());
        assertEquals(10000, findResult.getPoint());
    }

    public void 멤버십조회_사이즈가0() {
        // given

        // when
        List<Membership> result = membershipRepository.findAllByUserId("userId");

        // then
        assertEquals(0, result);
    }

    public void 멤버십조회_사이즈가2() {
        // given
        final Membership naverMembership = Membership.builder()
                                                     .userId("userId")
                                                     .membershipType(MembershipType.NAVER)
                                                     .point(10000)
                                                     .build();

        final Membership kakaoMembership = Membership.builder()
                                                     .userId("userId")
                                                     .membershipType(MembershipType.KAKAO)
                                                     .point(10000)
                                                     .build();

        membershipRepository.save(naverMembership);
        membershipRepository.save(kakaoMembership);

        // when
        List<Membership> result = membershipRepository.findAllByUserId("userId");

        // then
        assertEquals(2, result.size());
    }
}