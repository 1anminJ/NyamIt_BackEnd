package com.minjeong.nyamit_be.controller;

import com.minjeong.nyamit_be.dto.LoginDTO;
import com.minjeong.nyamit_be.dto.ProfileUpdateDTO;
import com.minjeong.nyamit_be.dto.SignUpDTO;
import com.minjeong.nyamit_be.service.UserService;
import com.minjeong.nyamit_be.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpDTO dto) {
        userService.registerUser(dto.getUserId(), dto.getPassword(), dto.getName(), dto.getEmail());
        return ResponseEntity.ok("회원가입 성공");
    } 

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO dto) {
        String token = userService.loginUser(dto.getUserId(), dto.getPassword());
        return ResponseEntity.ok(token);
    }


    @PutMapping("/profile")
    public ResponseEntity<String> updateProfile(
            @RequestHeader("Authorization") String token,
            @RequestBody ProfileUpdateDTO dto) {

        String jwt = token.replace("Bearer ", "");
        Long userId = Long.parseLong(jwtUtil.getUserIdFromToken(jwt));

        userService.updateProfile(userId, dto);
        return ResponseEntity.ok("프로필 수정 성공");
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        Long userId = Long.parseLong(jwtUtil.getUserIdFromToken(jwt));
        return ResponseEntity.ok(userService.getProfile(userId));
    }

}
