package com.minjeong.nyamit_be.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityDTO {
    private Long id;
    private String title;
    private String content;
    private String authorName;
    private Long userId;
    private LocalDateTime createdAt;
}


