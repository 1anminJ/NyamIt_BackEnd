package com.minjeong.nyamit_be.repository;

import com.minjeong.nyamit_be.entity.FastingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FastingRecordRepository extends JpaRepository<FastingRecord, Long> {
    List<FastingRecord> findByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end);

    Optional<FastingRecord> findByUserIdAndDate(Long userId, LocalDate date);
}

