package com.minjeong.nyamit_be.dto;

import com.minjeong.nyamit_be.entity.User;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
    private boolean active;
    private BigDecimal height;  // ✅ 추가
    private BigDecimal weight;  // ✅ 추가

    public static UserResponseDTO fromEntity(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .active(user.isActive())
                .height(user.getHeight() != null ? user.getHeight() : BigDecimal.ZERO)  // ✅ null 방지
                .weight(user.getWeight() != null ? user.getWeight() : BigDecimal.ZERO)
                .build();
    }
}


