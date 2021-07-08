package com.mercadolibre.fresco.util;

import com.mercadolibre.fresco.dtos.InboundOrderDTO;
import com.mercadolibre.fresco.dtos.SectionDTO;
import com.mercadolibre.fresco.dtos.StockDTO;
import com.mercadolibre.fresco.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static StockDTO createStockDTO(){
        return new StockDTO(25, "BANANA", 10.0, -10.0, 20, 10, 10.5, LocalDate.parse("2021-10-10"), LocalTime.parse("08:50:20"), LocalDate.parse("2021-10-10"));
    }

    public static List<StockDTO> createListStockDTO(){
        List<StockDTO> stocks = new ArrayList<>();
        stocks.add(createStockDTO());
        return stocks;
    }

    public static SectionDTO createSectionDTO(){
        return new SectionDTO("FS", "WAREHOUSE_TESTE");
    }

    public static InboundOrderDTO createInboundOrderDTO(){
        SectionDTO sectionDTO = createSectionDTO();
        List<StockDTO> batchStock = createListStockDTO();
        return new InboundOrderDTO(123L, LocalDate.parse("2021-10-10"), sectionDTO, batchStock);
    }

    public static User createUser(){
        return new User(1L, "testRep", "teste1000", null, null,null,null);
    }

    public static ProductCategory createProductCategory(){
        return new ProductCategory(1L, "FS", "Fresh", null, null);
    }

    public static Product createProduct(){
        ProductCategory productCategory = createProductCategory();
        return new Product(1L,"BANANA", null, 5., null, null, productCategory);
    }

    public static Warehouse createWarehouse(){
        User user = createUser();
        return new Warehouse(1L,"WAREHOUSE_TESTE", null, user);
    }

    public static Section createSection(){
        ProductCategory productCategory = createProductCategory();
        return new Section(1L, "FS", productCategory, null);
    }

    public static WarehouseSection createWarehouseSection(){
        Warehouse warehouse = createWarehouse();
        return new WarehouseSection(1L, null, warehouse, null);
    }

    public static Stock createStock(){
        Product product = createProduct();
        return new Stock().toBuilder().batchNumber(1).id(1L).currentQuantity(50).initialQuantity(50)
            .currentTemperature(10.).product(product).dueDate(LocalDate.parse("2021-10-25"))
            .manufacturingTime(LocalTime.now()).manufacturingDate(LocalDate.now()).build();
    }

    public static List<Stock> createListStock(){
        Stock stock = createStock();
        List<Stock> stocks = new ArrayList<>();
        stocks.add(stock);
        return stocks;
    }

    public static List<Product> createListProduct(){
        Product product = createProduct();
        List<Product> products = new ArrayList<>();
        products.add(product);
        return products;
    }
}
