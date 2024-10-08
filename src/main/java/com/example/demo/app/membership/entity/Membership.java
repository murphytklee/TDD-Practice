package com.example.demo.app.membership.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
public class Membership {

    /**
     * @Id
     * - javax.persistence.Id: 관계형 DB에서 사용.
     * - org.springframework.data.annotation.Id : JPA에 의해 지원되지 않는 Nosql이나 프레임워크에서 사용됨.
     */
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
