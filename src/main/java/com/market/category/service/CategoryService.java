package com.market.category.service;

import com.market.category.dto.request.CategoryRequestDto;
import com.market.category.dto.response.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAllCategories();

    CategoryDto getCategoryById(Long id);

    CategoryDto createOrChangeCategory(Long id, CategoryRequestDto request);

    Boolean deleteCategoryById(Long id);
}
