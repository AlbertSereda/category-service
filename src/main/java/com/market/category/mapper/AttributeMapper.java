package com.market.category.mapper;

import com.market.category.dto.request.AttributeRequestDto;
import com.market.category.dto.response.AttributeDto;
import com.market.category.dto.response.AttributeWithGroupDto;
import com.market.category.dto.response.GroupWithAttributesDto;
import com.market.category.jpa.entity.AttributeEntity;
import com.market.category.jpa.entity.AttributeGroupEntity;
import com.market.category.jpa.entity.CategoryEntity;
import org.mapstruct.*;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttributeMapper {

    AttributeDto toAttributeDto(AttributeEntity attributeEntity);

    @Mapping(target = "attributeGroupDto", source = "group")
    AttributeWithGroupDto toAttributeWithGroupDto(AttributeEntity attributeEntity);

    default List<AttributeDto> toAttributeDtoList(List<AttributeEntity> attributeEntities) {
        return attributeEntities.stream()
                                .map(this::toAttributeDto)
                                .sorted(Comparator.comparing(AttributeDto::getName))
                                .toList();
    }

    @Named("toAttributeDtoWithGroupList")
    default List<GroupWithAttributesDto> toAttributeDtoWithGroupList(CategoryEntity categoryEntity) {
        List<GroupWithAttributesDto> result = null;
        Set<AttributeEntity> attributes = categoryEntity.getAttributes();
        if (!CollectionUtils.isEmpty(attributes)) {
            Map<AttributeGroupEntity, List<AttributeEntity>> groupedAttributes = attributes
                    .stream()
                    .filter(e -> Objects.nonNull(e.getGroup()))
                    .collect(Collectors.groupingBy(AttributeEntity::getGroup,
                                                   Collectors.toList()));

            if (!CollectionUtils.isEmpty(groupedAttributes)) {
                result = toAttributeGroupDtoList(groupedAttributes);
            }
        }
        return result;
    }

    @Named("toAttributeDtoWithoutGroupList")
    default List<AttributeDto> toAttributeDtoWithoutGroupList(CategoryEntity categoryEntity) {
        List<AttributeDto> result = null;
        Set<AttributeEntity> attributes = categoryEntity.getAttributes();
        if (!CollectionUtils.isEmpty(attributes)) {
            List<AttributeEntity> attributesWithoutGroup = attributes.stream()
                                                                     .filter(e -> Objects.isNull(e.getGroup()))
                                                                     .collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(attributesWithoutGroup)) {
                result = toAttributeDtoList(attributesWithoutGroup);
            }
        }
        return result;
    }

    default List<GroupWithAttributesDto> toAttributeGroupDtoList(Map<AttributeGroupEntity, List<AttributeEntity>> groupedAttributes) {
        return groupedAttributes.entrySet()
                                .stream()
                                .map(e -> {
                                    AttributeGroupEntity group = e.getKey();
                                    List<AttributeEntity> attributeEntities = e.getValue();
                                    return new GroupWithAttributesDto(group.getId(),
                                                                      group.getName(),
                                                                      toAttributeDtoList(attributeEntities));
                                })
                                .sorted(Comparator.comparing(GroupWithAttributesDto::getName))
                                .toList();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "isArchive", ignore = true)
    void updateAttributeEntity(@MappingTarget AttributeEntity attributeEntity, AttributeRequestDto request, AttributeGroupEntity group);
}
