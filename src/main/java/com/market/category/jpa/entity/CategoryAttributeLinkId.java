package com.market.category.jpa.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CategoryAttributeLinkId implements Serializable {

    private Long categoryId;

    private Long attributeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryAttributeLinkId that = (CategoryAttributeLinkId) o;
        return categoryId.equals(that.categoryId) && attributeId.equals(that.attributeId);
    }

    @Override
    public int hashCode() {
        return categoryId.hashCode() + attributeId.hashCode();
    }
}
