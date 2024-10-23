package com.example.demo.app.point.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.app.point.service.impl.RatePointService;

@ExtendWith(MockitoExtension.class)
public class RatePointServiceTest {
    
    @InjectMocks
    private RatePointService pointService;

    @Test
    public void _10000원적립은100원() {
        // given
        final int price = 10000;

        // when
        final int result = pointService.calculateAmount(price);

        // then
        assertEquals(100, result);
    }
}
