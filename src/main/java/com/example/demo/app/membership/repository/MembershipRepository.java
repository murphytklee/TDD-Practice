package com.example.demo.app.membership.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.app.membership.entity.Membership;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
    
}
