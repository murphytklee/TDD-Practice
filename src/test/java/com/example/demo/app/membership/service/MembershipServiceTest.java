package com.example.demo.app.membership.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.app.enums.MembershipType;
import com.example.demo.app.membership.dto.MembershipAddResponse;
import com.example.demo.app.membership.dto.MembershipDetailResponse;
import com.example.demo.app.membership.entity.Membership;
import com.example.demo.app.membership.repository.MembershipRepository;
import com.example.demo.app.point.service.impl.RatePointService;
import com.example.demo.exception.MembershipErrorResult;
import com.example.demo.exception.MembershipException;

@ExtendWith(MockitoExtension.class)
public class MembershipServiceTest {

    private final String userId = "userId";
    private final MembershipType membershipType = MembershipType.NAVER;
    private final Integer point = 10000;
    private final Long membershipId = -1L;

    @InjectMocks
    private MembershipService target;
    
    @Mock
    private MembershipRepository membershipRepository;

    @Mock
    private RatePointService ratePointService;

    @Test
    public void 멤버십등록실패_이미존재함() {
        // given
        doReturn(Membership.builder().build()).when(membershipRepository).findByUserIdAndMembershipType(userId, membershipType);

        // when
        final MembershipException result = assertThrows(MembershipException.class, () -> target.addMembership(userId, membershipType, point));

        // then
        assertEquals(MembershipErrorResult.DUPLICATED_MEMBERSHIP_REGISTER, result.getErrorResult());
    }

    @Test 
    public void 멤버십등록성공() {
        // given
        doReturn(null).when(membershipRepository).findByUserIdAndMembershipType(userId, membershipType);
        doReturn(membership()).when(membershipRepository).save(any(Membership.class));

        // when
        final MembershipAddResponse result = target.addMembership(userId, membershipType, point);

        // then
        assertNotNull(result.getId());
        assertEquals(MembershipType.NAVER, result.getMembershipType());

        //verify
        verify(membershipRepository, times(1)).findByUserIdAndMembershipType(userId, membershipType);
        verify(membershipRepository, times(1)).save(any(Membership.class));
    }

    private Membership membership() {
        return Membership.builder()
                .id(-1L)
                .userId(userId)
                .point(point)
                .membershipType(MembershipType.NAVER)
                .build();
    }

    @Test
    public void 멤버십목록조회() {
        // given
        doReturn(Arrays.asList(
            Membership.builder().build(),
            Membership.builder().build(),
            Membership.builder().build()
        )).when(membershipRepository).findAllByUserId(userId);

        // when
        final List<MembershipDetailResponse> result = target.getMembershipList(userId);

        // then
        assertEquals(3, result.size());
    }

    @Test
    public void 멤버십상세조회실패_존재하지않음() {
        // given
        doReturn(Optional.empty()).when(membershipRepository).findById(membershipId);

        // when
        final MembershipException result = assertThrows(MembershipException.class, () -> target.getMembership(membershipId, userId));

        // then
        assertEquals(MembershipErrorResult.MEMBERSHIP_NOT_FOUND, result.getErrorResult()); 
    }

    @Test
    public void 멤버십상세조회실패_본인이아님() {
        // given
        doReturn(Optional.empty()).when(membershipRepository).findById(membershipId);

        // when
        final MembershipException result = assertThrows(MembershipException.class, () -> target.getMembership(membershipId, userId));

        // then
        assertEquals(MembershipErrorResult.MEMBERSHIP_NOT_FOUND, result.getErrorResult()); 
    }

    @Test
    public void 멤버십조회성공() {
        // given
        doReturn(Optional.of(membership())).when(membershipRepository).findById(membershipId);

        // when
        final MembershipDetailResponse result = target.getMembership(membershipId, userId);

        // then
        assertEquals(MembershipType.NAVER, result.getMembershipType());
        assertEquals(point, result.getPoint());
    }

    @Test
    public void 멤버십삭제실패_존재하지않음() {
        // given
        doReturn(Optional.empty()).when(membershipRepository).findById(membershipId);
    
        // when
        final MembershipException result = assertThrows(MembershipException.class, () -> target.removeMembership(membershipId, userId));
    
        // then
        assertEquals(MembershipErrorResult.MEMBERSHIP_NOT_FOUND, result.getErrorResult());
    }

    @Test
    public void 멤버십삭제실패_본인이아님() {
        // given
        final Membership membership = membership();
        doReturn(Optional.of(membership)).when(membershipRepository).findById(membershipId);

        // when
        final MembershipException result = assertThrows(MembershipException.class, () -> target.removeMembership(membershipId, "notowner"));

        // then
        assertEquals(MembershipErrorResult.NOT_MEMBERSHIP_OWNER, result.getErrorResult());
    }

    @Test
    public void 멤버십삭제성공() {
        // given
        final Membership membership = membership();
        doReturn(Optional.of(membership)).when(membershipRepository).findById(membershipId);

        // when
        target.removeMembership(membershipId, userId);
    }

    @Test
    public void 멤버십적립실패_존재하지않음() {
        // given
        doReturn(Optional.empty()).when(membershipRepository).findById(membershipId);

        // when
        final MembershipException result = assertThrows(MembershipException.class, () -> target.accumulateMembershipPoint(membershipId, userId, 10000));

        // then
        assertEquals(MembershipErrorResult.MEMBERSHIP_NOT_FOUND, result.getErrorResult());
    }

    @Test
    public void 멤버십적립실패_본인이아님() {
        // given
        final Membership membership = membership();
        doReturn(Optional.of(membership)).when(membershipRepository).findById(membershipId);

        // when
        final MembershipException result = assertThrows(MembershipException.class, () -> target.accumulateMembershipPoint(membershipId, "notowner", 10000));

        // then
        assertEquals(MembershipErrorResult.NOT_MEMBERSHIP_OWNER, result.getErrorResult());
    }

    @Test
    public void 멤버십적립성공() {
        // given
        final Membership membership = membership();
        doReturn(Optional.of(membership)).when(membershipRepository).findById(membershipId);

        // when
        target.accumulateMembershipPoint(membershipId, userId, 10000);
    }
}
