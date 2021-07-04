package com.mercadolibre.fresco.util;

import com.mercadolibre.fresco.dtos.SectionDTO;
import com.mercadolibre.fresco.dtos.StockDTO;
import com.mercadolibre.fresco.model.Stock;

public interface StockUtils {
    Stock convertDtoToEntity(StockDTO stockDTO, SectionDTO sectionDTO);
}
