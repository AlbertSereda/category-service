package com.market.category.jpa.repository;

import com.market.category.jpa.entity.AttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttributeRepository extends JpaRepository<AttributeEntity, Long> {

    List<AttributeEntity> findAllByIsArchiveIsFalse();

    @Modifying
    @Query(value = "update AttributeEntity set isArchive = true where id = :id")
    void archiveAttribute(@Param("id") Long id);

    @Modifying
    @Query(value = "update AttributeEntity set group = null where group.id = :attributeGroupId")
    void clearAttributeGroup(@Param("attributeGroupId") Long attributeGroupId);
}
