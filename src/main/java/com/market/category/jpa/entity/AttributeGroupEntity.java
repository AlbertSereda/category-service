package com.market.category.jpa.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "attribute_group")
@Schema(description = "Attribute group information")
public class AttributeGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Schema(description = "Unique identifier for the attribute group")
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    @Schema(description = "Name of the attribute group")
    private String name;
}
