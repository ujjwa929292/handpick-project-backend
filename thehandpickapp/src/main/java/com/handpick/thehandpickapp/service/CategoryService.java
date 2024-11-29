package com.handpick.thehandpickapp.service;
import java.util.List;
import java.util.UUID;

import com.handpick.thehandpickapp.accessors.models.Category;
import com.handpick.thehandpickapp.models.CategoryDTO;

public interface CategoryService {
    Category createCategory(CategoryDTO category);
    Category updateCategory(UUID id, CategoryDTO category);
    void deleteCategory(UUID id);
    Category getCategoryById(UUID id);
    List<Category> getAllCategories();
}

