package com.mercadolibre.fresco.repository;

import com.mercadolibre.fresco.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    @Query(value = "SELECT * FROM fresh_sections WHERE section_id = :id", nativeQuery = true)
    Section findById(@Param("id") String id);

    Section findBySectionCode(String code);
}
