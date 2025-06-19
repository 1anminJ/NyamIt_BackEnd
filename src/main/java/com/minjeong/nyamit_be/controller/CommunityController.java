package com.minjeong.nyamit_be.controller;


import com.minjeong.nyamit_be.dto.CommunityDTO;
import com.minjeong.nyamit_be.service.CommunityService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class CommunityController {
    private final CommunityService communityService;

    @GetMapping
    public List<CommunityDTO> getAll() {
        return communityService.getAllPosts();
    }

    @PostMapping
    public CommunityDTO create(@RequestBody CommunityDTO dto, HttpServletRequest request) {
        return communityService.createPost(dto, request);
    }

    @PutMapping("/{id}")
    public CommunityDTO update(@PathVariable Long id, @RequestBody CommunityDTO dto, HttpServletRequest request) {
        return communityService.updatePost(id, dto, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest request) {
        communityService.deletePost(id, request);
        return ResponseEntity.ok().build();
    }
}
