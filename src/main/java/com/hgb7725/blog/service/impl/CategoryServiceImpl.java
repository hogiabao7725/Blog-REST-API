package com.hgb7725.blog.service.impl;

import com.hgb7725.blog.entity.Category;
import com.hgb7725.blog.exception.BlogAPIException;
import com.hgb7725.blog.exception.ResourceNotFoundException;
import com.hgb7725.blog.payload.CategoryDTO;
import com.hgb7725.blog.repository.CategoryRepository;
import com.hgb7725.blog.service.CategoryService;
import com.hgb7725.blog.utils.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        if(categoryDTO.getId() != null) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Resource (Category) ID must not be provided for creation");
        }
        Category category = mapper.map(categoryDTO, Category.class);
        return mapper.map(categoryRepository.save(category), CategoryDTO.class);
    }

    @Transactional(readOnly = true)
    @Override
    public CategoryDTO getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.RESOURCE_CATEGORY, Constants.FIELD_ID, categoryId));
        return mapper.map(category, CategoryDTO.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> list = categoryRepository.findAll();
        return list.stream().map(category -> mapper.map(category,CategoryDTO.class)).toList();
    }

    @Transactional
    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.RESOURCE_CATEGORY, Constants.FIELD_ID, categoryId));
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return mapper.map(category, CategoryDTO.class);
    }

    @Transactional
    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.RESOURCE_CATEGORY, Constants.FIELD_ID, categoryId));
        categoryRepository.delete(category);
    }
}
