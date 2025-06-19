package com.minjeong.nyamit_be.dto;

import lombok.*;
import jakarta.validation.constraints.Digits;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProfileResponseDTO {
    private String name;
    private String email;

    private BigDecimal height;

    private BigDecimal weight;
}

