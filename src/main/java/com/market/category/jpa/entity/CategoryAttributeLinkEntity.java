package com.market.category.jpa.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "category_attribute_link")
@Schema(description = "Links categories to attributes")
public class CategoryAttributeLinkEntity {

    @EmbeddedId
    private CategoryAttributeLinkId id;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id", nullable = false)
    @Schema(description = "Identifier of the category")
    private CategoryEntity category;

    @ManyToOne
    @MapsId("attributeId")
    @JoinColumn(name = "attribute_id", nullable = false)
    @Schema(description = "Identifier of the attribute")
    private AttributeEntity attribute;
}
