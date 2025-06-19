package com.minjeong.nyamit_be.dto;

import com.minjeong.nyamit_be.entity.FastingRecord;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FastingWeeklyResponseDTO {
    private List<FastingDayDTO> records;
    private long fastingDays;

    public static FastingWeeklyResponseDTO from(List<FastingRecord> records, long fastingDays) {
        List<FastingDayDTO> list = records.stream()
                .map(r -> new FastingDayDTO(r.getDate(), r.getDurationMinutes()))
                .collect(Collectors.toList());

        return FastingWeeklyResponseDTO.builder()
                .records(list)
                .fastingDays(fastingDays)
                .build();
    }

    @Getter
    @AllArgsConstructor
    public static class FastingDayDTO {
        private LocalDate date;
        private int durationMinutes;
    }
}
