package com.minjeong.nyamit_be.repository;


import com.minjeong.nyamit_be.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {
}

