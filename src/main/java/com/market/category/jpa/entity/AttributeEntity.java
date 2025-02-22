package com.market.category.jpa.entity;

import com.market.category.consts.DataType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "attribute")
@Schema(description = "Attribute information")
public class AttributeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Unique identifier for the attribute")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    @Schema(description = "Name of the attribute")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_type", nullable = false)
    @Schema(description = "Type of data the attribute")
    private DataType dataType;

    @Column(name = "description")
    @Schema(description = "Description of the attribute")
    private String description;

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    @Schema(description = "Identifier of the attribute group")
    private AttributeGroupEntity group;

    @Column(name = "creation_date", nullable = false, updatable = false)
    @CreationTimestamp
    @Schema(description = "Date of creation")
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(name = "update_date", nullable = false)
    @UpdateTimestamp
    @Schema(description = "Date of the last update")
    private LocalDateTime updateDate = LocalDateTime.now();

    @Column(name = "is_archive", nullable = false)
    @Schema(description = "Archive status of the attribute")
    private Boolean isArchive = false;
}
