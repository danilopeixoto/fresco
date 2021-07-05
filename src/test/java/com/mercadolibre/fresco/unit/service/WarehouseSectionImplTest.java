package com.mercadolibre.fresco.unit.service;

import com.mercadolibre.fresco.model.Section;
import com.mercadolibre.fresco.model.Warehouse;
import com.mercadolibre.fresco.model.WarehouseSection;
import com.mercadolibre.fresco.repository.WarehouseSectionRepository;
import com.mercadolibre.fresco.service.crud.impl.WarehouseSectionImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class WarehouseSectionImplTest {

  WarehouseSectionRepository warehouseSectionRepository = Mockito.mock(WarehouseSectionRepository.class);
  WarehouseSectionImpl warehouseSection;

  @BeforeEach
  void setup() {
    this.warehouseSection = new WarehouseSectionImpl(warehouseSectionRepository);
  }

  @Test
  void shouldFindByWarehouseAndSectionId() {
    Warehouse warehouse = new Warehouse(1L, "WAREHOUSE_TESTE", null);
    Section section = new Section(1L, "REFRIGERADOS", null);
    WarehouseSection warehouseSection = new WarehouseSection(1L, section, warehouse, null);
    when(warehouseSectionRepository.findByWarehouseAndSectionId(1L, 1L)).thenReturn(warehouseSection);
    WarehouseSection testWarehouseSection = this.warehouseSection.findByWarehouseAndSectionId(1L, 1L);
    assertEquals(1L, testWarehouseSection.getId());
    assertEquals("REFRIGERADOS", testWarehouseSection.getSection().getSectionCode());
    assertEquals("WAREHOUSE_TESTE", testWarehouseSection.getWarehouse().getWarehouseCode());
  }

}
