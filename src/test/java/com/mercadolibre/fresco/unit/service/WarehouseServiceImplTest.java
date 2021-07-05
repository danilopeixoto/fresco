package com.mercadolibre.fresco.unit.service;

import com.mercadolibre.fresco.model.Warehouse;
import com.mercadolibre.fresco.repository.WarehouseRepository;
import com.mercadolibre.fresco.service.crud.impl.WarehouseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class WarehouseServiceImplTest {

    WarehouseRepository warehouseRepository = Mockito.mock(WarehouseRepository.class);
    WarehouseServiceImpl warehouseService;

    @BeforeEach
    void setup() {
        this.warehouseService = new WarehouseServiceImpl(warehouseRepository);
    }

    @Test
    void shouldFindWarehouseByCode() {
        Warehouse warehouse = new Warehouse(1L, "WAREHOUSE_TESTE", null);
        when(warehouseRepository.getWarehouseIdByCode("WAREHOUSE_TESTE")).thenReturn(warehouse.getId());
        Long id = this.warehouseService.getWarehouseIdByCode("WAREHOUSE_TESTE");
        assertEquals(1, id);
    }


}
