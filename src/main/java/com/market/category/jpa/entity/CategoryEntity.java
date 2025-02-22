package com.market.category.jpa.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "category")
@Schema(description = "Category information")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Unique identifier for the category")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    @Schema(description = "Name of the category")
    private String name;

    @Column(name = "description")
    @Schema(description = "Description of the category")
    private String description;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @Schema(description = "Parent category ID (for hierarchical structure)")
    private CategoryEntity parent;

    @Column(name = "creation_date", nullable = false, updatable = false)
    @CreationTimestamp
    @Schema(description = "Date of creation")
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(name = "update_date", nullable = false)
    @UpdateTimestamp
    @Schema(description = "Date of the last update")
    private LocalDateTime updateDate = LocalDateTime.now();

    @Column(name = "is_archive", nullable = false)
    @Schema(description = "Archive status of the category")
    private Boolean isArchive = false;

    @ManyToMany
    @JoinTable(
            name = "category_attribute_link",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_id")
    )
    @Schema(description = "List of attributes linked to the category")
    private Set<AttributeEntity> attributes;
}
