package com.handpick.thehandpickapp.accessors;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.handpick.thehandpickapp.accessors.models.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, UUID> {
}