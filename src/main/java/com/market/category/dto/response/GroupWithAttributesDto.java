package com.market.category.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Группа атрибутов со списком привязанных атрибутов")
public class GroupWithAttributesDto extends AttributeGroupDto {

    @Schema(description = "Атрибуты группы")
    private List<AttributeDto> attributes;

    public GroupWithAttributesDto(Long id, String name, List<AttributeDto> attributes) {
        super(id, name);
        this.attributes = attributes;
    }
}
