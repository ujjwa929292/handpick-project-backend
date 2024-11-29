package com.handpick.thehandpickapp.accessors;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.handpick.thehandpickapp.accessors.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
}