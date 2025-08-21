package com.market.category.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "тип данных атрибута")
public class DataTypeDto {

    @Schema(description = "id типа данных")
    private String id;

    @Schema(description = "Название типа данных")
    private String name;
}
