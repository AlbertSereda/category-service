package com.market.category.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO запроса на создание или изменение категории")
public class CategoryRequestDto {

    @Schema(description = "Название категории", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String name;

    @Schema(description = "Описание категории")
    private String description;

    @Schema(description = "Id родительской категории")
    private Long parentId;

    @Schema(description = "Атрибуты привязанные к категории")
    private Set<Long> attributeIds;
}
