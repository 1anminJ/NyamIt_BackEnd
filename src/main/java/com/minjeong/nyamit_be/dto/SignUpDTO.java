package com.minjeong.nyamit_be.dto;

import lombok.Data;

@Data
public class SignUpDTO {
    private String userId;
    private String password;
    private String name;
    private String email;
}
