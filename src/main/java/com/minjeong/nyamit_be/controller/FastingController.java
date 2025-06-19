package com.minjeong.nyamit_be.controller;

import com.minjeong.nyamit_be.dto.FastingRequestDTO;
import com.minjeong.nyamit_be.dto.FastingWeeklyResponseDTO;
import com.minjeong.nyamit_be.entity.FastingRecord;
import com.minjeong.nyamit_be.security.JwtUtil;
import com.minjeong.nyamit_be.service.FastingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fasting")
public class FastingController {

    private final FastingService fastingService;
    private final JwtUtil jwtUtil;

    // 단식 기록 저장 (JWT에서 userId 추출)
    @PostMapping("/record")
    public ResponseEntity<String> recordFasting(@RequestBody FastingRequestDTO dto,
                                                HttpServletRequest request) {
        Long userId = jwtUtil.extractUserIdFromRequest(request);
        fastingService.saveFastingRecord(userId, dto.getDurationMinutes());
        return ResponseEntity.ok("단식 기록 저장 완료");
    }

    // 이번 주 단식 기록 조회
    @GetMapping("/weekly")
    public ResponseEntity<FastingWeeklyResponseDTO> getWeeklyFasting(HttpServletRequest request) {
        Long userId = jwtUtil.extractUserIdFromRequest(request);

        List<FastingRecord> records = fastingService.getWeeklyRecords(userId);
        long fastingDays = fastingService.countFastingDaysThisWeek(userId);

        return ResponseEntity.ok(FastingWeeklyResponseDTO.from(records, fastingDays));
    }
}
