package com.mercadolibre.fresco.unit.service;

import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.model.Warehouse;
import com.mercadolibre.fresco.repository.WarehouseRepository;
import com.mercadolibre.fresco.service.crud.impl.WarehouseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class WarehouseServiceImplTest {

    WarehouseRepository warehouseRepository = Mockito.mock(WarehouseRepository.class);
    WarehouseServiceImpl warehouseService;

    @BeforeEach
    void setup() {
        this.warehouseService = new WarehouseServiceImpl(warehouseRepository);
    }


    @Test
    void shouldThrowNotFoundException() {
        Warehouse warehouse = new Warehouse(1L, "WAREHOUSE_TESTE", null, null);
        when(warehouseRepository.findWarehouseByCode("WAREHOUSE_TESTE")).thenReturn(warehouse);
        assertThrows(NotFoundException.class, () -> this.warehouseService.findWarehouseByCode("FAIL"));
    }

    @Test
    void shouldGetWarehouseId() {
        Warehouse warehouse = new Warehouse(1L, "WAREHOUSE_TESTE", null, null);
        when(warehouseRepository.findWarehouseByCode("WAREHOUSE_TESTE")).thenReturn(warehouse);
        Long id = this.warehouseService.getWarehouseIdByCode("WAREHOUSE_TESTE");
        assertEquals(1L, id);
    }

}
