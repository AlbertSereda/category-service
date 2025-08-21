package com.market.category.consts;

import com.market.category.dto.response.DataTypeDto;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum DataType {
    STRING("Строковый тип данных"),
    BOOLEAN("Логический тип данных"),
    NUMBER("Числовой тип данных");

    private final String description;

    DataType(String description) {
        this.description = description;
    }

    public static List<DataTypeDto> getAllDataTypes() {
        return Stream.of(DataType.values())
                     .map(dataType -> new DataTypeDto(dataType.name(), dataType.description))
                     .sorted(Comparator.comparing(DataTypeDto::getName))
                     .collect(Collectors.toList());
    }
}
