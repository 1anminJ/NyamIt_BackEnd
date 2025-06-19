package com.minjeong.nyamit_be.service;

import com.minjeong.nyamit_be.dto.CommunityDTO;
import com.minjeong.nyamit_be.entity.Community;
import com.minjeong.nyamit_be.entity.User;
import com.minjeong.nyamit_be.repository.CommunityRepository;
import com.minjeong.nyamit_be.repository.UserRepository;
import com.minjeong.nyamit_be.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public List<CommunityDTO> getAllPosts() {
        return communityRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public CommunityDTO createPost(CommunityDTO dto, HttpServletRequest request) {
        Long userId = jwtUtil.extractUserIdFromRequest(request);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Community post = Community.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        return toDto(communityRepository.save(post));
    }

    public CommunityDTO updatePost(Long postId, CommunityDTO dto, HttpServletRequest request) {
        Long userId = jwtUtil.extractUserIdFromRequest(request);
        Community post = communityRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        if (!post.getUser().getId().equals(userId)) {
            throw new IllegalStateException("작성자만 수정할 수 있습니다.");
        }

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());

        return toDto(communityRepository.save(post));
    }

    public void deletePost(Long postId, HttpServletRequest request) {
        Long userId = jwtUtil.extractUserIdFromRequest(request);
        Community post = communityRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        if (!post.getUser().getId().equals(userId)) {
            throw new IllegalStateException("작성자만 삭제할 수 있습니다.");
        }

        communityRepository.delete(post);
    }

    private CommunityDTO toDto(Community post) {
        return CommunityDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .authorName(post.getUser().getName())
                .userId(post.getUser().getId())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
