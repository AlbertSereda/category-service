package com.market.category.service.impl;

import com.market.category.dto.request.CategoryRequestDto;
import com.market.category.dto.response.CategoryDto;
import com.market.category.exception.EntityNotFoundException;
import com.market.category.jpa.entity.AttributeEntity;
import com.market.category.jpa.entity.CategoryEntity;
import com.market.category.jpa.repository.CategoryRepository;
import com.market.category.mapper.CategoryMapper;
import com.market.category.service.AttributeService;
import com.market.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final AttributeService attributeService;

    @Override
    @Transactional
    public List<CategoryDto> getAllCategories() {
        return categoryMapper.toCategoryDtoList(categoryRepository.findAllByIsArchiveIsFalse());
    }

    @Override
    @Transactional
    public CategoryDto getCategoryById(Long id) {
        CategoryEntity categoryEntity = findById(id);
        return categoryMapper.toDto(categoryEntity);
    }

    private CategoryEntity findById(Long id) {
        return categoryRepository.findById(id)
                                 .orElseThrow(() -> EntityNotFoundException.categoryNotFound(id));
    }

    @Override
    @Transactional
    public CategoryDto createOrChangeCategory(Long id, CategoryRequestDto request) {
        // TODO если добавили в категорию атрибутов, то
        // отправлять уведомление по кафке, что надо добавить атрибуты в продукты с пустыми value
        Long parentId = request.getParentId();
        CategoryEntity parent = null;
        if (Objects.nonNull(parentId)) {
            parent = findById(parentId);
        }

        List<AttributeEntity> attributeEntities = null;
        Set<Long> attributeIds = request.getAttributeIds();
        if (CollectionUtils.isNotEmpty(attributeIds)) {
            attributeEntities = attributeService.findAllByIds(attributeIds);
        }

        CategoryEntity category;
        if (Objects.nonNull(id)) {
            category = findById(id);
            categoryMapper.updateEntity(request, parent, attributeEntities, category);
        } else {
            category = categoryMapper.toNewEntity(request, parent, attributeEntities);
        }

        category = categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    @Transactional
    public Boolean deleteCategoryById(Long id) {
        // TODO уведомление по кафке что категории больше нет, возможно надо указывать замену при удалении
        categoryRepository.archiveCategory(id);
        return true;
    }
}
