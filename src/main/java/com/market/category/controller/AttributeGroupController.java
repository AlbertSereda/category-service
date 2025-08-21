package com.market.category.controller;

import com.market.category.dto.ResponseObjectDto;
import com.market.category.dto.request.AttributeGroupRequestDto;
import com.market.category.dto.response.AttributeGroupDto;
import com.market.category.service.AttributeGroupService;
import com.market.category.service.AttributeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/attribute-group")
@RequiredArgsConstructor
@Tag(name = "Attribute group controller", description = "Методы для управления группами атрибутов")
public class AttributeGroupController {

    private final AttributeGroupService attributeGroupService;

    private final AttributeService attributeService;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Группы найдены"),
            @ApiResponse(responseCode = "500", description = "Непредвиденная ошибка")
    })
    @Operation(summary = "Получение всех групп")
    // TODO сделать с пагинацией и фильтрацией
    public ResponseEntity<ResponseObjectDto<List<AttributeGroupDto>>> getAll() {
        List<AttributeGroupDto> groups = attributeGroupService.getAllGroups();
        ResponseObjectDto<List<AttributeGroupDto>> body = ResponseObjectDto.success(groups, groups.size());
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Группа найдена"),
            @ApiResponse(responseCode = "404", description = "Группа не найдена"),
            @ApiResponse(responseCode = "500", description = "Непредвиденная ошибка")
    })
    @Operation(summary = "Получение группы по id")
    public ResponseEntity<ResponseObjectDto<AttributeGroupDto>> getById(@PathVariable Long id) {
        AttributeGroupDto groupDto = attributeGroupService.getGroupById(id);
        ResponseObjectDto<AttributeGroupDto> body = ResponseObjectDto.success(groupDto);
        return ResponseEntity.ok(body);
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Группа создана"),
            @ApiResponse(responseCode = "400", description = "Не заполнены обязательные поля"),
            @ApiResponse(responseCode = "500", description = "Непредвиденная ошибка")
    })
    @Operation(summary = "Создать группу")
    public ResponseEntity<ResponseObjectDto<AttributeGroupDto>> createGroup(@Valid @RequestBody
                                                                            AttributeGroupRequestDto request) {
        AttributeGroupDto groupDto = attributeGroupService.createOrChangeGroup(null, request);
        ResponseObjectDto<AttributeGroupDto> body = ResponseObjectDto.success(groupDto);
        return ResponseEntity.status(HttpStatus.CREATED.value())
                             .body(body);
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Группа изменена"),
            @ApiResponse(responseCode = "400", description = "Не заполнены обязательные поля"),
            @ApiResponse(responseCode = "404", description = "Группа не найдена"),
            @ApiResponse(responseCode = "500", description = "Непредвиденная ошибка")
    })
    @Operation(summary = "Изменить группу")
    public ResponseEntity<ResponseObjectDto<AttributeGroupDto>> changeGroup(@PathVariable Long id,
                                                                            @Valid @RequestBody AttributeGroupRequestDto request) {
        AttributeGroupDto groupDto = attributeGroupService.createOrChangeGroup(id, request);
        ResponseObjectDto<AttributeGroupDto> body = ResponseObjectDto.success(groupDto);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Группа удалена"),
            @ApiResponse(responseCode = "500", description = "Непредвиденная ошибка")
    })
    @Operation(summary = "Удалить группу по id")
    public ResponseEntity<ResponseObjectDto<Boolean>> deleteGroup(@PathVariable Long id) {
        Boolean result = attributeService.deleteGroupById(id);
        ResponseObjectDto<Boolean> body = ResponseObjectDto.success(result);
        return ResponseEntity.ok(body);
    }
}
