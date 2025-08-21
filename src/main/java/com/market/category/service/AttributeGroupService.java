package com.market.category.service;

import com.market.category.dto.request.AttributeGroupRequestDto;
import com.market.category.dto.response.AttributeGroupDto;
import com.market.category.jpa.entity.AttributeGroupEntity;
import jakarta.validation.Valid;

import java.util.List;

public interface AttributeGroupService {

    AttributeGroupEntity findById(Long groupId);

    List<AttributeGroupDto> getAllGroups();

    AttributeGroupDto getGroupById(Long id);

    AttributeGroupDto createOrChangeGroup(Long id, @Valid AttributeGroupRequestDto request);

    void deleteGroupById(Long id);
}
