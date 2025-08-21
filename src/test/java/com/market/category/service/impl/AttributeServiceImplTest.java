package com.market.category.service.impl;

import com.market.category.consts.DataType;
import com.market.category.exception.EntityNotFoundException;
import com.market.category.jpa.entity.AttributeEntity;
import com.market.category.jpa.entity.AttributeGroupEntity;
import com.market.category.jpa.repository.AttributeRepository;
import com.market.category.mapper.AttributeMapper;
import com.market.category.service.AttributeGroupService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AttributeServiceImplTest {

    @Mock
    private AttributeRepository attributeRepository;

    @Mock
    private AttributeMapper attributeMapper;

    @Mock
    private AttributeGroupService attributeGroupService;

    @InjectMocks
    private AttributeServiceImpl attributeServiceImpl;

    @Test
    public void findById_shouldReturnAttribute() {
        Long findId = 1L;
        AttributeEntity expectedEntity = new AttributeEntity();
        expectedEntity.setId(findId);
        expectedEntity.setName("name");
        expectedEntity.setDataType(DataType.BOOLEAN);
        expectedEntity.setDescription("description");

        AttributeGroupEntity attributeGroupEntity = new AttributeGroupEntity(2L, "group");
        expectedEntity.setGroup(attributeGroupEntity);

        expectedEntity.setCreationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        expectedEntity.setUpdateDate(LocalDateTime.of(2, 2, 2, 2, 2));
        expectedEntity.setIsArchive(false);

        when(attributeRepository.findById(eq(findId))).thenReturn(Optional.of(expectedEntity));

        AttributeEntity actualEntity = attributeServiceImpl.findById(findId);

        assertEquals(actualEntity, expectedEntity);
    }

    @Test
    public void findById_shouldReturnNotFoundException() {
        Long findId = 1L;

        when(attributeRepository.findById(eq(findId))).thenReturn(Optional.empty());

        try {
            attributeServiceImpl.findById(findId);
        } catch (EntityNotFoundException e) {
            EntityNotFoundException entityNotFoundException = EntityNotFoundException.attributeNotFound(findId);
            assertEquals(entityNotFoundException.getMessage(), e.getMessage());
        }
    }

    @Test
    public void findAllByIds_shouldReturnAttributeList() {
        Long findId = 1L;
        AttributeEntity expectedEntity = new AttributeEntity();
        expectedEntity.setId(findId);
        expectedEntity.setName("name");
        expectedEntity.setDataType(DataType.BOOLEAN);
        expectedEntity.setDescription("description");

        AttributeGroupEntity attributeGroupEntity = new AttributeGroupEntity(2L, "group");
        expectedEntity.setGroup(attributeGroupEntity);

        expectedEntity.setCreationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        expectedEntity.setUpdateDate(LocalDateTime.of(2, 2, 2, 2, 2));
        expectedEntity.setIsArchive(false);
        List<AttributeEntity> expectedEntityList = List.of(expectedEntity);

        when(attributeRepository.findAllById(eq(Set.of(findId)))).thenReturn(expectedEntityList);

        List<AttributeEntity> actualList = attributeServiceImpl.findAllByIds(Set.of(findId));

        assertIterableEquals(actualList, expectedEntityList);
    }
}