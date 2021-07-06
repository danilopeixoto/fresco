package com.mercadolibre.fresco.controller;


import com.mercadolibre.fresco.dtos.response.ProductResponseDTO;
import com.mercadolibre.fresco.dtos.response.WarehousesProductCountResponseDTO;
import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.exceptions.UnauthorizedException;
import com.mercadolibre.fresco.service.IWarehouseCatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Warehouse catalog")
@RequestMapping(path = "/api/v1/warehouse")
@RestController
public class WarehouseCatalogController {
    private final IWarehouseCatalogService warehouseCatalogService;

    public WarehouseCatalogController(IWarehouseCatalogService warehouseCatalogService) {
        this.warehouseCatalogService = warehouseCatalogService;
    }

    /**
     * ================================
     * Count products by product code and group by warehouse code
     *
     * @param productCode
     * @return WarehousesProductCountResponseDTO
     */
    @Operation(summary = "List products by category", responses = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = ProductResponseDTO.class)),
                            mediaType = "application/json")),
            @ApiResponse(
                    responseCode = "401",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = ProductResponseDTO.class)),
                            mediaType = "application/json")),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = ProductResponseDTO.class)),
                            mediaType = "application/json"))
    })
    @PreAuthorize("hasAuthority('REP')")
    @GetMapping
    @ResponseBody
    public WarehousesProductCountResponseDTO groupCount(@RequestParam(required = true) String productCode)
            throws NotFoundException, UnauthorizedException {
        return this.warehouseCatalogService.groupByWarehouseCodeCountByProductCode(productCode);
    }
}
