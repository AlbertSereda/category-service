package com.market.category.dto.response;

import com.market.category.consts.DataType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Атрибут с группой")
public class AttributeWithGroupDto extends AttributeDto {

    @Schema(description = "Группа к которой привязан атрибут")
    private AttributeGroupDto attributeGroupDto;

    public AttributeWithGroupDto(Long id,
                                 String name,
                                 DataType dataType,
                                 String description,
                                 AttributeGroupDto attributeGroupDto) {
        super(id, name, dataType, description);
        this.attributeGroupDto = attributeGroupDto;
    }
}
