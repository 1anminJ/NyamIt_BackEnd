package com.minjeong.nyamit_be.service;

import com.minjeong.nyamit_be.dto.ProfileResponseDTO;
import com.minjeong.nyamit_be.dto.ProfileUpdateDTO;
import com.minjeong.nyamit_be.dto.UserResponseDTO;
import com.minjeong.nyamit_be.entity.User;
import com.minjeong.nyamit_be.repository.UserRepository;
import com.minjeong.nyamit_be.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService { 

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void registerUser(String userId, String password, String name, String email) {
        if (userRepository.existsByUserId(userId)) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User user = User.builder()
                .userId(userId)
                .password(passwordEncoder.encode(password))
                .role("USER")
                .name(name)
                .active(true)
                .email(email)
                .build();

        userRepository.save(user);
    }

    public String loginUser(String userId, String password) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // ✅ 여기서 role도 JWT에 포함해서 발급
        return jwtUtil.generateToken(user.getId(), user.getName(), user.getRole());
    }


    @Transactional
    public void updateProfile(Long id, ProfileUpdateDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.setHeight(dto.getHeight());
        user.setWeight(dto.getWeight());
    }

    public ProfileResponseDTO getProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));

        return new ProfileResponseDTO(
                user.getName(),
                user.getEmail(),
                user.getHeight() != null ? user.getHeight() : BigDecimal.ZERO,
                user.getWeight() != null ? user.getWeight() : BigDecimal.ZERO
        );
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        users.forEach(u -> System.out.println("✅ user: " + u.getId() + ", " + u.getHeight() + ", " + u.getWeight()));
        return users.stream()
                .map(UserResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }


    public void deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        user.setActive(false);
        userRepository.save(user);
    }

    public void activateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        user.setActive(true);
        userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        userRepository.delete(user);
    }
}
