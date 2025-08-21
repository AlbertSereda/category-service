package com.market.category.controller;

import com.market.category.consts.DataType;
import com.market.category.dto.ResponseObjectDto;
import com.market.category.dto.response.DataTypeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/dict")
@RequiredArgsConstructor
@Tag(name = "Dict controller", description = "Методы для получения справочников")
public class DictController {

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Типы данных атрибутов найдены"),
            @ApiResponse(responseCode = "500", description = "Непредвиденная ошибка")
    })
    @Operation(summary = "Получение всех типов данных атрибутов")
    public ResponseEntity<ResponseObjectDto<List<DataTypeDto>>> getAll() {
        List<DataTypeDto> dataTypeDtos = DataType.getAllDataTypes();
        ResponseObjectDto<List<DataTypeDto>> body = ResponseObjectDto.success(dataTypeDtos, dataTypeDtos.size());
        return ResponseEntity.ok(body);
    }
}
