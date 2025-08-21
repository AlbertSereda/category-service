package com.market.category.mapper;

import com.market.category.dto.request.CategoryRequestDto;
import com.market.category.dto.response.CategoryDto;
import com.market.category.jpa.entity.AttributeEntity;
import com.market.category.jpa.entity.CategoryEntity;
import org.mapstruct.*;

import java.util.Comparator;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {AttributeMapper.class})
public interface CategoryMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "nestedCategories", source = "nestedCategories", qualifiedByName = "toCategoryDtoList")
    @Mapping(target = "attributeGroups", source = "categoryEntity", qualifiedByName = "toAttributeDtoWithGroupList")
    @Mapping(target = "attributes", source = "categoryEntity", qualifiedByName = "toAttributeDtoWithoutGroupList")
    CategoryDto toDto(CategoryEntity categoryEntity);

    @Named("toCategoryDtoList")
    default List<CategoryDto> toCategoryDtoList(List<CategoryEntity> categoryEntityList) {
        return categoryEntityList.stream()
                                 .map(this::toDto)
                                 .sorted(Comparator.comparing(CategoryDto::getName))
                                 .toList();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "parentCategory", source = "parent")
    @Mapping(target = "nestedCategories", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "isArchive", ignore = true)
    @Mapping(target = "attributes", source = "attributeEntities")
    CategoryEntity toNewEntity(CategoryRequestDto request,
                               CategoryEntity parent,
                               List<AttributeEntity> attributeEntities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "parentCategory", source = "parent")
    @Mapping(target = "nestedCategories", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "isArchive", ignore = true)
    @Mapping(target = "attributes", source = "attributeEntities")
    void updateEntity(CategoryRequestDto request,
                      CategoryEntity parent,
                      List<AttributeEntity> attributeEntities,
                      @MappingTarget CategoryEntity category);
}
