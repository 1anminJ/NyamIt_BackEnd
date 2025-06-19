package com.minjeong.nyamit_be.dto;

import jakarta.validation.constraints.Digits;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateDTO {
    @Digits(integer = 3, fraction = 1)
    private BigDecimal height;

    @Digits(integer = 3, fraction = 1)
    private BigDecimal weight;
}

