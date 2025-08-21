package com.market.category.dto.response;

import com.market.category.consts.DataType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Атрибут")
public class AttributeDto {

    @Schema(description = "id атрибута")
    private Long id;

    @Schema(description = "Название атрибута")
    private String name;

    @Schema(description = "Тип данных атрибута")
    private DataType dataType;

    @Schema(description = "Описание атрибута")
    private String description;
}
