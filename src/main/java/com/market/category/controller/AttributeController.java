package com.market.category.controller;

import com.market.category.dto.ResponseObjectDto;
import com.market.category.dto.request.AttributeRequestDto;
import com.market.category.dto.response.AttributeWithGroupDto;
import com.market.category.service.AttributeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/attribute")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Attribute controller", description = "Методы для управления атрибутами")
public class AttributeController {

    private final AttributeService attributeService;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Атрибуты найдены"),
            @ApiResponse(responseCode = "500", description = "Непредвиденная ошибка")
    })
    @Operation(summary = "Получение всех атрибутов")
    // TODO сделать с пагинацией и фильтрацией
    public ResponseEntity<ResponseObjectDto<List<AttributeWithGroupDto>>> getAll() {
        List<AttributeWithGroupDto> attributes = attributeService.getAllAttributes();
        ResponseObjectDto<List<AttributeWithGroupDto>> body = ResponseObjectDto.success(attributes, attributes.size());
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Атрибут найден"),
            @ApiResponse(responseCode = "404", description = "Атрибут не найден"),
            @ApiResponse(responseCode = "500", description = "Непредвиденная ошибка")
    })
    @Operation(summary = "Получение атрибута по id")
    public ResponseEntity<ResponseObjectDto<AttributeWithGroupDto>> getById(@PathVariable Long id) {
        AttributeWithGroupDto attribute = attributeService.getAttributeById(id);
        ResponseObjectDto<AttributeWithGroupDto> body = ResponseObjectDto.success(attribute);
        return ResponseEntity.ok(body);
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Атрибут создан"),
            @ApiResponse(responseCode = "400", description = "Не заполнены обязательные поля"),
            @ApiResponse(responseCode = "500", description = "Непредвиденная ошибка")
    })
    @Operation(summary = "Создать атрибут")
    public ResponseEntity<ResponseObjectDto<AttributeWithGroupDto>> createAttribute(@Valid @RequestBody
                                                                                    AttributeRequestDto request) {
        AttributeWithGroupDto attribute = attributeService.createOrChangeAttribute(null, request);
        ResponseObjectDto<AttributeWithGroupDto> body = ResponseObjectDto.success(attribute);
        return ResponseEntity.status(HttpStatus.CREATED.value())
                             .body(body);
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Атрибут изменен"),
            @ApiResponse(responseCode = "400", description = "Не заполнены обязательные поля"),
            @ApiResponse(responseCode = "404", description = "Атрибут не найден"),
            @ApiResponse(responseCode = "500", description = "Непредвиденная ошибка")
    })
    @Operation(summary = "Изменить атрибут")
    public ResponseEntity<ResponseObjectDto<AttributeWithGroupDto>> changeAttribute(@PathVariable Long id,
                                                                                    @Valid @RequestBody
                                                                                    AttributeRequestDto request) {
        AttributeWithGroupDto attribute = attributeService.createOrChangeAttribute(id, request);
        ResponseObjectDto<AttributeWithGroupDto> body = ResponseObjectDto.success(attribute);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Атрибут удален"),
            @ApiResponse(responseCode = "500", description = "Непредвиденная ошибка")
    })
    @Operation(summary = "Удалить атрибут по id")
    public ResponseEntity<ResponseObjectDto<Boolean>> deleteAttribute(@PathVariable Long id) {
        Boolean result = attributeService.deleteAttributeById(id);
        ResponseObjectDto<Boolean> body = ResponseObjectDto.success(result);
        return ResponseEntity.ok(body);
    }
}
