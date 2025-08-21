package com.market.category.controller;

import com.market.category.dto.ResponseObjectDto;
import com.market.category.dto.request.CategoryRequestDto;
import com.market.category.dto.response.CategoryDto;
import com.market.category.service.CategoryService;
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
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
@Tag(name = "Category controller", description = "Методы для управления категориями")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Категории найдены"),
            @ApiResponse(responseCode = "500", description = "Непредвиденная ошибка")
    })
    @Operation(summary = "Получение всех категорий")
    // TODO сделать с пагинацией и фильтрацией
    public ResponseEntity<ResponseObjectDto<List<CategoryDto>>> getAll() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        ResponseObjectDto<List<CategoryDto>> body = ResponseObjectDto.success(categories, categories.size());
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Категория найдена"),
            @ApiResponse(responseCode = "404", description = "Категория не найдена"),
            @ApiResponse(responseCode = "500", description = "Непредвиденная ошибка")
    })
    @Operation(summary = "Получение категории по id")
    public ResponseEntity<ResponseObjectDto<CategoryDto>> getById(@PathVariable Long id) {
        CategoryDto category = categoryService.getCategoryById(id);
        ResponseObjectDto<CategoryDto> body = ResponseObjectDto.success(category);
        return ResponseEntity.ok(body);
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Категория создана"),
            @ApiResponse(responseCode = "400", description = "Не заполнены обязательные поля"),
            @ApiResponse(responseCode = "500", description = "Непредвиденная ошибка")
    })
    @Operation(summary = "Создать категорию")
    public ResponseEntity<ResponseObjectDto<CategoryDto>> createCategory(@Valid @RequestBody CategoryRequestDto request) {
        CategoryDto category = categoryService.createOrChangeCategory(null, request);
        ResponseObjectDto<CategoryDto> body = ResponseObjectDto.success(category);
        return ResponseEntity.status(HttpStatus.CREATED.value())
                             .body(body);
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Категория изменена"),
            @ApiResponse(responseCode = "400", description = "Не заполнены обязательные поля"),
            @ApiResponse(responseCode = "404", description = "Категория не найдена"),
            @ApiResponse(responseCode = "500", description = "Непредвиденная ошибка")
    })
    @Operation(summary = "Изменить категорию")
    public ResponseEntity<ResponseObjectDto<CategoryDto>> changeCategory(@PathVariable Long id,
                                                                         @Valid @RequestBody CategoryRequestDto request) {
        CategoryDto category = categoryService.createOrChangeCategory(id, request);
        ResponseObjectDto<CategoryDto> body = ResponseObjectDto.success(category);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Категория удалена"),
            @ApiResponse(responseCode = "500", description = "Непредвиденная ошибка")
    })
    @Operation(summary = "Удалить категорию по id")
    public ResponseEntity<ResponseObjectDto<Boolean>> deleteCategory(@PathVariable Long id) {
        Boolean result = categoryService.deleteCategoryById(id);
        ResponseObjectDto<Boolean> body = ResponseObjectDto.success(result);
        return ResponseEntity.ok(body);
    }
}
