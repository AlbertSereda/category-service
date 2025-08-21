package com.market.category.dto.request;

import com.market.category.consts.DataType;
import com.market.category.consts.ValidationErrorMessageConst;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO запроса на создание или изменение атрибута")
public class AttributeRequestDto {

    @Schema(description = "Название атрибута", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = ValidationErrorMessageConst.ATTRIBUTE_NAME_ERROR)
    private String name;

    @Schema(description = "Тип данных атрибута", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = ValidationErrorMessageConst.ATTRIBUTE_DATA_TYPE_ERROR)
    private DataType dataType;

    @Schema(description = "Описание категории")
    private String description;

    @Schema(description = "Id родительской категории")
    private Long groupId;
}
