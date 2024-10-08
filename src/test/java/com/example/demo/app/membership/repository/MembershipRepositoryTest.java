package com.example.demo.app.membership.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MembershipRepositoryTest {
    
    @Autowired
    private MembershipRepository membershipRepository;
    
}