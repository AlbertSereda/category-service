package com.market.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO ответа")
public class ResponseObjectDto<T> {

    @Schema(description = "Признак успеха запроса")
    private boolean success;

    @Schema(description = "Объект-результат запроса")
    private T result;

    @Schema(description = "Размер результата запроса")
    private Integer total;

    @Schema(description = "Сообщение об ошибке")
    private Set<String> errors;

    @Schema(description = "Статус код")
    private Integer statusCode;

    public static <T> ResponseObjectDto<T> success(T result) {
        return new ResponseObjectDto<>(true, result, null, null, null);
    }

    public static <T> ResponseObjectDto<T> success(T result, Integer total) {
        return new ResponseObjectDto<>(true, result, total, null, null);
    }

    public static <T> ResponseObjectDto<T> error(HttpStatus status) {
        return new ResponseObjectDto<>(false, null, null, Set.of(status.getReasonPhrase()), status.value());
    }

    public static <T> ResponseObjectDto<T> error(String errorMessage, HttpStatus status) {
        return new ResponseObjectDto<>(false, null, null, Set.of(errorMessage), status.value());
    }

    public static <T> ResponseObjectDto<T> error(Set<String> errors, HttpStatus status) {
        return new ResponseObjectDto<>(false, null, null, errors, status.value());
    }
}
