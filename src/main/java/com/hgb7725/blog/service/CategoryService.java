package com.hgb7725.blog.service;

import com.hgb7725.blog.payload.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO getCategory(Long id);

    List<CategoryDTO> getAllCategories();

    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);

    void deleteCategory(Long categoryId);
}
