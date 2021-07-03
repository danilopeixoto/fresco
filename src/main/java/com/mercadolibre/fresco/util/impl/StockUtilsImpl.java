package com.mercadolibre.fresco.util.impl;

/*import com.mercadolibre.fresco.dtos.SectionDTO;
import com.mercadolibre.fresco.dtos.StockDTO;
import com.mercadolibre.fresco.model.Stock;
import com.mercadolibre.fresco.service.crud.IProductService;
import com.mercadolibre.fresco.service.crud.ISectionService;
import com.mercadolibre.fresco.service.crud.IWarehouseSection;
import com.mercadolibre.fresco.service.crud.IWarehouseService;
import com.mercadolibre.fresco.util.StockUtils;

public class StockUtilsImpl implements StockUtils {

    private final IProductService productService;
    private final IWarehouseService warehouseService;
    private final ISectionService sectionService;
    private final IWarehouseSection warehouseSection;

    public StockUtilsImpl(IProductService productService, IWarehouseService warehouseService,
                          ISectionService sectionService, IWarehouseSection warehouseSection) {
        this.productService = productService;
        this.warehouseService = warehouseService;
        this.sectionService = sectionService;
        this.warehouseSection = warehouseSection;
    }

    @Override
    public Stock convertDtoToEntity(StockDTO stockDTO, SectionDTO sectionDTO) {
        Stock stock = new Stock();
        stock.setCurrentQuantity(stockDTO.getCurrentQuantity());
        stock.setCurrentTemperature(stockDTO.getCurrentTemperature());
        stock.setInitialQuantity(stockDTO.getInitialQuantity());
        stock.setProduct(this.productService.findByProductCode(stockDTO.getProductCode()));
        stock.setWarehouseSection(this.warehouseSection.findByWarehouseAndSectionId(
                warehouseService.getWarehouseIdByCode(sectionDTO.warehouseCode),
                sectionService.getIdBySectionCode(sectionDTO.sectionCode)
        ));
        return stock;
    }
}*/
