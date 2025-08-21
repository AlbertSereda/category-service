package com.market.category.jpa.repository;

import com.market.category.jpa.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    List<CategoryEntity> findAllByIsArchiveIsFalse();

    @Modifying
    @Query(value = "update CategoryEntity set isArchive = true where id = :id")
    void archiveCategory(@Param("id") Long id);
}
