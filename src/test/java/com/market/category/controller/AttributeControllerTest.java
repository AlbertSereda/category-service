package com.market.category.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.category.consts.DataType;
import com.market.category.consts.ExceptionMessageType;
import com.market.category.consts.ValidationErrorMessageConst;
import com.market.category.dto.request.AttributeRequestDto;
import com.market.category.dto.response.AttributeGroupDto;
import com.market.category.dto.response.AttributeWithGroupDto;
import com.market.category.exception.EntityNotFoundException;
import com.market.category.service.AttributeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AttributeController.class)
public class AttributeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AttributeService attributeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAll_shouldReturnTwoAttributes() throws Exception {
        List<AttributeWithGroupDto> attributes = new ArrayList<>();
        AttributeWithGroupDto attribute1 = new AttributeWithGroupDto(1L, "attribute 1", DataType.NUMBER, null, null);
        AttributeWithGroupDto attribute2 = new AttributeWithGroupDto(2L, "attribute 2", DataType.BOOLEAN, "description", new AttributeGroupDto(1L, "group 1"));
        attributes.add(attribute1);
        attributes.add(attribute2);

        when(attributeService.getAllAttributes()).thenReturn(attributes);

        mockMvc.perform(get("/api/v1/attribute"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value(true))
               .andExpect(jsonPath("$.total").value(2))
               .andExpect(jsonPath("$.result.[0].id").value(1L))
               .andExpect(jsonPath("$.result.[0].name").value("attribute 1"))
               .andExpect(jsonPath("$.result.[0].dataType").value(DataType.NUMBER.name()))
               .andExpect(jsonPath("$.result.[0].description").isEmpty())
               .andExpect(jsonPath("$.result.[0].attributeGroupDto").isEmpty())
               .andExpect(jsonPath("$.result.[1].id").value(2L))
               .andExpect(jsonPath("$.result.[1].name").value("attribute 2"))
               .andExpect(jsonPath("$.result.[1].dataType").value(DataType.BOOLEAN.name()))
               .andExpect(jsonPath("$.result.[1].description").value("description"))
               .andExpect(jsonPath("$.result.[1].attributeGroupDto").isNotEmpty())
               .andExpect(jsonPath("$.result.[1].attributeGroupDto.id").value(1L))
               .andExpect(jsonPath("$.result.[1].attributeGroupDto.name").value("group 1"))
               .andExpect(jsonPath("$.errors").isEmpty());
    }

    @Test
    public void getAll_shouldReturnEmptyList() throws Exception {
        List<AttributeWithGroupDto> attributes = new ArrayList<>();

        when(attributeService.getAllAttributes()).thenReturn(attributes);

        mockMvc.perform(get("/api/v1/attribute"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value(true))
               .andExpect(jsonPath("$.total").value(0))
               .andExpect(jsonPath("$.result").isArray())
               .andExpect(jsonPath("$.result").isEmpty())
               .andExpect(jsonPath("$.errors").isEmpty());
    }
    @ParameterizedTest
    @MethodSource("uriPathHttpMethodSource")
    public void getAll_shouldReturnError(String uriPath, String httpMethod) throws Exception {
        String internalServerError = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        when(attributeService.getAllAttributes()).thenThrow(new RuntimeException(internalServerError));
        when(attributeService.getAttributeById(anyLong())).thenThrow(new RuntimeException(internalServerError));
        when(attributeService.createOrChangeAttribute(anyLong(), any())).thenThrow(new RuntimeException(internalServerError));
        when(attributeService.createOrChangeAttribute(isNull(), any())).thenThrow(new RuntimeException(internalServerError));
        when(attributeService.deleteAttributeById(anyLong())).thenThrow(new RuntimeException(internalServerError));

        ResultActions resultActions;
        switch (httpMethod) {
            case "GET" -> resultActions = mockMvc.perform(get(uriPath));
            case "POST" -> {
                AttributeRequestDto requestDto = new AttributeRequestDto("attribute 2", DataType.BOOLEAN, "description", 1L);
                resultActions = mockMvc.perform(post(uriPath).contentType(MediaType.APPLICATION_JSON)
                                                             .content(objectMapper.writeValueAsString(requestDto)));
            }
            case "PUT" -> {
                AttributeRequestDto requestDto = new AttributeRequestDto("attribute 2", DataType.BOOLEAN, "description", 1L);
                resultActions = mockMvc.perform(put(uriPath).contentType(MediaType.APPLICATION_JSON)
                                                            .content(objectMapper.writeValueAsString(requestDto)));
            }
            case "DELETE" -> resultActions = mockMvc.perform(delete(uriPath));
            default -> throw new RuntimeException("Неизвестный HTTP метод");
        }
        resultActions
               .andExpect(status().isInternalServerError())
               .andExpect(jsonPath("$.success").value(false))
               .andExpect(jsonPath("$.total").isEmpty())
               .andExpect(jsonPath("$.result").isEmpty())
               .andExpect(jsonPath("$.errors").value(internalServerError));
    }

    static Stream<Arguments> uriPathHttpMethodSource() {
        return Stream.of(Arguments.of("/api/v1/attribute", "GET"),
                         Arguments.of("/api/v1/attribute/2", "GET"),
                         Arguments.of("/api/v1/attribute", "POST"),
                         Arguments.of("/api/v1/attribute/3", "PUT"),
                         Arguments.of("/api/v1/attribute/4", "DELETE"));
    }

    @Test
    public void getById_shouldReturnAttribute() throws Exception {
        Long findId = 2L;
        AttributeWithGroupDto attribute = new AttributeWithGroupDto(2L, "attribute 2", DataType.BOOLEAN, "description", new AttributeGroupDto(1L, "group 1"));

        when(attributeService.getAttributeById(eq(findId))).thenReturn(attribute);

        mockMvc.perform(get("/api/v1/attribute/{id}", findId))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value(true))
               .andExpect(jsonPath("$.result.id").value(2L))
               .andExpect(jsonPath("$.result.name").value("attribute 2"))
               .andExpect(jsonPath("$.result.dataType").value(DataType.BOOLEAN.name()))
               .andExpect(jsonPath("$.result.description").value("description"))
               .andExpect(jsonPath("$.result.attributeGroupDto").isNotEmpty())
               .andExpect(jsonPath("$.result.attributeGroupDto.id").value(1L))
               .andExpect(jsonPath("$.result.attributeGroupDto.name").value("group 1"))
               .andExpect(jsonPath("$.errors").isEmpty());
    }

    @Test
    public void getById_shouldReturnNotFoundError() throws Exception {
        Long findId = 2L;
        when(attributeService.getAttributeById(eq(findId))).thenThrow(EntityNotFoundException.attributeNotFound(findId));

        String expectErrorMessage = String.format(ExceptionMessageType.ATTRIBUTE_NOT_FOUND.getErrorMessage(), findId);
        mockMvc.perform(get("/api/v1/attribute/{id}", findId))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.success").value(false))
               .andExpect(jsonPath("$.total").isEmpty())
               .andExpect(jsonPath("$.result").isEmpty())
               .andExpect(jsonPath("$.errors").value(expectErrorMessage));
    }

    @Test
    public void createAttribute_shouldCreateAttribute() throws Exception {
        AttributeRequestDto requestDto = new AttributeRequestDto("attribute 2", DataType.BOOLEAN, "description", 1L);
        AttributeWithGroupDto attribute = new AttributeWithGroupDto(2L, "attribute 2", DataType.BOOLEAN, "description", new AttributeGroupDto(1L, "group 1"));

        when(attributeService.createOrChangeAttribute(isNull(), eq(requestDto))).thenReturn(attribute);

        mockMvc.perform(post("/api/v1/attribute")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.success").value(true))
               .andExpect(jsonPath("$.result.id").value(2L))
               .andExpect(jsonPath("$.result.name").value("attribute 2"))
               .andExpect(jsonPath("$.result.dataType").value(DataType.BOOLEAN.name()))
               .andExpect(jsonPath("$.result.description").value("description"))
               .andExpect(jsonPath("$.result.attributeGroupDto").isNotEmpty())
               .andExpect(jsonPath("$.result.attributeGroupDto.id").value(1L))
               .andExpect(jsonPath("$.result.attributeGroupDto.name").value("group 1"))
               .andExpect(jsonPath("$.errors").isEmpty());
    }

    @Test
    public void createAttribute_shouldReturnAttributeNameError() throws Exception {
        AttributeRequestDto requestDto = new AttributeRequestDto(null, DataType.BOOLEAN, "description", 1L);

        mockMvc.perform(post("/api/v1/attribute")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.success").value(false))
               .andExpect(jsonPath("$.total").isEmpty())
               .andExpect(jsonPath("$.result").isEmpty())
               .andExpect(jsonPath("$.errors").isNotEmpty())
               .andExpect(jsonPath("$.errors.[0]").value(ValidationErrorMessageConst.ATTRIBUTE_NAME_ERROR));
    }

    @Test
    public void createAttribute_shouldReturnAttributeDataTypeError() throws Exception {
        AttributeRequestDto requestDto = new AttributeRequestDto("attribute", null, "description", 1L);

        mockMvc.perform(post("/api/v1/attribute")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.success").value(false))
               .andExpect(jsonPath("$.total").isEmpty())
               .andExpect(jsonPath("$.result").isEmpty())
               .andExpect(jsonPath("$.errors").isNotEmpty())
               .andExpect(jsonPath("$.errors.[0]").value(ValidationErrorMessageConst.ATTRIBUTE_DATA_TYPE_ERROR));
    }

    @Test
    public void changeAttribute_shouldChangeAttribute() throws Exception {
        Long findId = 2L;
        AttributeRequestDto requestDto = new AttributeRequestDto("attribute 2", DataType.BOOLEAN, "description", 1L);
        AttributeWithGroupDto attribute = new AttributeWithGroupDto(2L, "attribute 2", DataType.BOOLEAN, "description", new AttributeGroupDto(1L, "group 1"));

        when(attributeService.createOrChangeAttribute(eq(findId), eq(requestDto))).thenReturn(attribute);

        mockMvc.perform(put("/api/v1/attribute/{id}", findId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value(true))
               .andExpect(jsonPath("$.result.id").value(2L))
               .andExpect(jsonPath("$.result.name").value("attribute 2"))
               .andExpect(jsonPath("$.result.dataType").value(DataType.BOOLEAN.name()))
               .andExpect(jsonPath("$.result.description").value("description"))
               .andExpect(jsonPath("$.result.attributeGroupDto").isNotEmpty())
               .andExpect(jsonPath("$.result.attributeGroupDto.id").value(1L))
               .andExpect(jsonPath("$.result.attributeGroupDto.name").value("group 1"))
               .andExpect(jsonPath("$.errors").isEmpty());
    }

    @Test
    public void changeAttribute_shouldReturnAttributeNameError() throws Exception {
        AttributeRequestDto requestDto = new AttributeRequestDto(null, DataType.BOOLEAN, "description", 1L);

        mockMvc.perform(put("/api/v1/attribute/{id}", 2L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.success").value(false))
               .andExpect(jsonPath("$.total").isEmpty())
               .andExpect(jsonPath("$.result").isEmpty())
               .andExpect(jsonPath("$.errors").isNotEmpty())
               .andExpect(jsonPath("$.errors.[0]").value(ValidationErrorMessageConst.ATTRIBUTE_NAME_ERROR));
    }

    @Test
    public void changeAttribute_shouldReturnAttributeDataTypeError() throws Exception {
        AttributeRequestDto requestDto = new AttributeRequestDto("attribute", null, "description", 1L);

        mockMvc.perform(put("/api/v1/attribute/{id}", 2L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.success").value(false))
               .andExpect(jsonPath("$.total").isEmpty())
               .andExpect(jsonPath("$.result").isEmpty())
               .andExpect(jsonPath("$.errors").isNotEmpty())
               .andExpect(jsonPath("$.errors.[0]").value(ValidationErrorMessageConst.ATTRIBUTE_DATA_TYPE_ERROR));
    }

    @Test
    public void deleteAttribute_shouldDeleteAttribute() throws Exception {
        Long deleteId = 2L;
        when(attributeService.deleteAttributeById(eq(deleteId))).thenReturn(true);

        mockMvc.perform(delete("/api/v1/attribute/{id}", deleteId))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value(true))
               .andExpect(jsonPath("$.result").value(true))
               .andExpect(jsonPath("$.errors").isEmpty());
    }
}