package com.market.category.service.impl;

import com.market.category.dto.request.AttributeGroupRequestDto;
import com.market.category.dto.response.AttributeGroupDto;
import com.market.category.exception.EntityNotFoundException;
import com.market.category.jpa.entity.AttributeGroupEntity;
import com.market.category.jpa.repository.AttributeGroupRepository;
import com.market.category.service.AttributeGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AttributeGroupServiceImpl implements AttributeGroupService {

    private final AttributeGroupRepository attributeGroupRepository;

    @Override
    @Transactional
    public AttributeGroupEntity findById(Long groupId) {
        return attributeGroupRepository.findById(groupId)
                                       .orElseThrow(() -> EntityNotFoundException.attributeGroupNotFound(groupId));
    }

    @Override
    @Transactional
    public List<AttributeGroupDto> getAllGroups() {
        return attributeGroupRepository.findAll()
                                       .stream()
                                       .map(e -> new AttributeGroupDto(e.getId(), e.getName()))
                                       .toList();
    }

    @Override
    @Transactional
    public AttributeGroupDto getGroupById(Long id) {
        AttributeGroupEntity groupEntity = findById(id);
        return new AttributeGroupDto(groupEntity.getId(), groupEntity.getName());
    }

    @Override
    @Transactional
    public AttributeGroupDto createOrChangeGroup(Long id, AttributeGroupRequestDto request) {
        AttributeGroupEntity groupEntity;

        if (Objects.nonNull(id)) {
            groupEntity = findById(id);
        } else {
            groupEntity = new AttributeGroupEntity();
        }

        groupEntity.setName(request.getName());
        groupEntity = attributeGroupRepository.save(groupEntity);
        return new AttributeGroupDto(groupEntity.getId(), groupEntity.getName());
    }

    @Override
    @Transactional
    public void deleteGroupById(Long id) {
        attributeGroupRepository.deleteById(id);
    }
}
