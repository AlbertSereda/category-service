package com.market.category.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Категория")
public class CategoryDto {

    @Schema(description = "id категории")
    private Long id;

    @Schema(description = "Название категории")
    private String name;

    @Schema(description = "Описание категории")
    private String description;

    @Schema(description = "Вложенные категории")
    private List<CategoryDto> nestedCategories;

    @Schema(description = "Группы атрибутов")
    private List<GroupWithAttributesDto> attributeGroups;

    @Schema(description = "Атрибуты категории без группы")
    private List<AttributeDto> attributes;
}
