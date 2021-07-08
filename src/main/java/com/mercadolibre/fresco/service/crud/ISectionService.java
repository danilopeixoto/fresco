package com.mercadolibre.fresco.service.crud;

import com.mercadolibre.fresco.model.Section;
import org.springframework.data.repository.query.Param;

public interface ISectionService extends ICRUD<Section> {

    Long getIdBySectionCode(@Param("sectionCode") String sectionCode);

}
