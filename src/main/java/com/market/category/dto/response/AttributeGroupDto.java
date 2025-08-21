package com.market.category.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Группа атрибутов")
public class AttributeGroupDto {

    @Schema(description = "id группы атрибутов")
    private Long id;

    @Schema(description = "Название группы атрибутов")
    private String name;
}
