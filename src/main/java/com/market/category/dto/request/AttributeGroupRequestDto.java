package com.market.category.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO запроса на создание или изменение группы атрибутов")
public class AttributeGroupRequestDto {

    @Schema(description = "Название группы атрибутов", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String name;
}
