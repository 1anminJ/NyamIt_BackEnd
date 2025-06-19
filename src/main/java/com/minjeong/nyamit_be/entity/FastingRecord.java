package com.minjeong.nyamit_be.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;


@Entity
@Table(name = "fasting_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FastingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDate date; // 단식한 날짜

    @Column(nullable = false)
    private int durationMinutes; // 단식 시간 (분 단위)

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}

