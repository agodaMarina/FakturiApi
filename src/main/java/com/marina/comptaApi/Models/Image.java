package com.marina.comptaApi.Models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Builder
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Image implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column
    private String url;

    @CreatedDate
    private LocalDateTime createdAt;
}
