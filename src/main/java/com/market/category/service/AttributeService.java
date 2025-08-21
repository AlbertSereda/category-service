package com.market.category.service;

import com.market.category.dto.request.AttributeRequestDto;
import com.market.category.dto.response.AttributeWithGroupDto;
import com.market.category.jpa.entity.AttributeEntity;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Set;

public interface AttributeService {

    AttributeEntity findById(Long id);

    List<AttributeEntity> findAllByIds(Set<Long> ids);

    List<AttributeWithGroupDto> getAllAttributes();

    AttributeWithGroupDto getAttributeById(Long id);

    AttributeWithGroupDto createOrChangeAttribute(Long attributeId, @Valid AttributeRequestDto request);

    Boolean deleteAttributeById(Long id);

    Boolean deleteGroupById(Long id);
}
