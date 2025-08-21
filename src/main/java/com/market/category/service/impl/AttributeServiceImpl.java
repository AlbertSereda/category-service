package com.market.category.service.impl;

import com.market.category.dto.request.AttributeRequestDto;
import com.market.category.dto.response.AttributeWithGroupDto;
import com.market.category.exception.EntityNotFoundException;
import com.market.category.jpa.entity.AttributeEntity;
import com.market.category.jpa.entity.AttributeGroupEntity;
import com.market.category.jpa.repository.AttributeRepository;
import com.market.category.mapper.AttributeMapper;
import com.market.category.service.AttributeGroupService;
import com.market.category.service.AttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AttributeServiceImpl implements AttributeService {

    private final AttributeRepository attributeRepository;

    private final AttributeMapper attributeMapper;

    private final AttributeGroupService attributeGroupService;

    @Override
    @Transactional
    public AttributeEntity findById(Long id) {
        return attributeRepository.findById(id)
                                  .orElseThrow(() -> EntityNotFoundException.attributeNotFound(id));
    }

    @Override
    @Transactional
    public List<AttributeEntity> findAllByIds(Set<Long> ids) {
        return attributeRepository.findAllById(ids);
    }

    @Override
    @Transactional
    public List<AttributeWithGroupDto> getAllAttributes() {
        return attributeRepository.findAllByIsArchiveIsFalse()
                                  .stream()
                                  .map(attributeMapper::toAttributeWithGroupDto)
                                  .toList();
    }

    @Override
    @Transactional
    public AttributeWithGroupDto getAttributeById(Long id) {
        return attributeMapper.toAttributeWithGroupDto(findById(id));
    }

    @Override
    @Transactional
    public AttributeWithGroupDto createOrChangeAttribute(Long attributeId, AttributeRequestDto request) {
        AttributeEntity attributeEntity;
        if (Objects.nonNull(attributeId)) {
            attributeEntity = findById(attributeId);
        } else {
            attributeEntity = new AttributeEntity();
        }

        AttributeGroupEntity group = null;
        Long groupId = request.getGroupId();
        if (Objects.nonNull(groupId)) {
            group = attributeGroupService.findById(groupId);
        }

        attributeMapper.updateAttributeEntity(attributeEntity, request, group);
        attributeEntity = attributeRepository.save(attributeEntity);
        return attributeMapper.toAttributeWithGroupDto(attributeEntity);
    }

    @Override
    @Transactional
    public Boolean deleteAttributeById(Long id) {
        // TODO уведомление по кафке что атрибута больше нет, возможно надо указывать замену при удалении
        attributeRepository.archiveAttribute(id);
        return true;
    }

    private void clearAttributeGroup(Long attributeGroupId) {
        attributeRepository.clearAttributeGroup(attributeGroupId);
    }

    @Override
    @Transactional
    public Boolean deleteGroupById(Long id) {
        clearAttributeGroup(id);
        attributeGroupService.deleteGroupById(id);
        return true;
    }
}
