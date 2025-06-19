package com.minjeong.nyamit_be.service;

import com.minjeong.nyamit_be.entity.FastingRecord;
import com.minjeong.nyamit_be.repository.FastingRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FastingService {

    private final FastingRecordRepository fastingRecordRepository;

    public void saveFastingRecord(Long userId, int durationMinutes) {
        LocalDate today = LocalDate.now();

        FastingRecord record = fastingRecordRepository.findByUserIdAndDate(userId, today)
                .orElse(FastingRecord.builder()
                        .userId(userId)
                        .date(today)
                        .durationMinutes(durationMinutes)
                        .build());

        record.setDurationMinutes(durationMinutes); // 덮어쓰기
        fastingRecordRepository.save(record);
    }

    public List<FastingRecord> getWeeklyRecords(Long userId) {
        LocalDate today = LocalDate.now();
        LocalDate weekAgo = today.minusDays(6);
        return fastingRecordRepository.findByUserIdAndDateBetween(userId, weekAgo, today);
    }

    public long countFastingDaysThisWeek(Long userId) {
        return getWeeklyRecords(userId).stream()
                .filter(record -> record.getDurationMinutes() > 0)
                .count();
    }
}

