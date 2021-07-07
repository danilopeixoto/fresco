package com.mercadolibre.fresco.repository;

import com.mercadolibre.fresco.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    @Query(value = "SELECT id FROM sections WHERE section_code = :sectionCode", nativeQuery = true)
    Long getIdBySectionCode(@Param("sectionCode") String sectionCode);

    @Query(value = "SELECT * FROM sections WHERE section_code = :sectionCode", nativeQuery = true)
    Section getBySectionCode(@Param("sectionCode") String sectionCode);


}
