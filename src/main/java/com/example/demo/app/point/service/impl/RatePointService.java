package com.example.demo.app.point.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.app.point.service.PointService;

@Service
public class RatePointService implements PointService{

    private static final int POINT_RATE = 1;
    
    public int calculateAmount(final int price) {
        return price * POINT_RATE / 100;
    }
}
