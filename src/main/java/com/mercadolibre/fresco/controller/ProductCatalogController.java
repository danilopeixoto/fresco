package com.mercadolibre.fresco.controller;


import com.mercadolibre.fresco.dtos.response.ProductResponseDTO;
import com.mercadolibre.fresco.dtos.response.ProductStockResponseDTO;
import com.mercadolibre.fresco.dtos.response.aggregation.IBatchStockDueDateResponse;
import com.mercadolibre.fresco.exceptions.ApiError;
import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.model.enumeration.BatchStockOrder;
import com.mercadolibre.fresco.model.enumeration.EProductCategory;
import com.mercadolibre.fresco.model.enumeration.EResultOrder;
import com.mercadolibre.fresco.service.IProductCatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@Tag(name = "Product catalog")
@RequestMapping(path = "/api/v1/fresh-products")
@RestController
public class ProductCatalogController {
    private final IProductCatalogService productCatalogService;

    public ProductCatalogController(IProductCatalogService productCatalogService) {
        this.productCatalogService = productCatalogService;
    }

    /**
     * ================================
     * List all products
     *
     * @return List
     */
    @Operation(summary = "List all products", responses = {
        @ApiResponse(
            responseCode = "200",
            content = @Content(
                array = @ArraySchema(schema = @Schema(implementation = ProductResponseDTO.class)),
                mediaType = "application/json")),
        @ApiResponse(
            responseCode = "404",
            content = @Content(
                array = @ArraySchema(schema = @Schema(implementation = ProductResponseDTO.class)),
                mediaType = "application/json"))
    })
    @PreAuthorize("hasAuthority('BUYER')")
    @GetMapping
    @ResponseBody
    public List<ProductResponseDTO> listAll() throws NotFoundException {
        return this.productCatalogService.findAll();
    }

    /**
     * ================================
     * List products by category
     *
     * @param category
     * @return List
     */
    @Operation(summary = "List products by category", responses = {
        @ApiResponse(
            responseCode = "200",
            content = @Content(
                array = @ArraySchema(schema = @Schema(implementation = ProductResponseDTO.class)),
                mediaType = "application/json")),
        @ApiResponse(
            responseCode = "404",
            content = @Content(
                array = @ArraySchema(schema = @Schema(implementation = ProductResponseDTO.class)),
                mediaType = "application/json"))
    })
    @PreAuthorize("hasAuthority('BUYER')")
    @GetMapping(path = "/list")
    @ResponseBody
    public List<ProductResponseDTO> listByCategory(@RequestParam(required = true, defaultValue = "FS") EProductCategory category)
        throws NotFoundException {
        return this.productCatalogService.findProductsByCategoryCode(category);
    }

    /**
     * ================================
     * List stocks of a product by productCode
     *
     * @param productCode, order
     * @return List
     */
    @Operation(summary = "List stocks of a product by productCode", responses = {
        @ApiResponse(
            responseCode = "200",
            content = @Content(
                array = @ArraySchema(schema = @Schema(implementation = ProductStockResponseDTO.class)),
                mediaType = "application/json")),
        @ApiResponse(
            responseCode = "404",
            content = @Content(
                schema = @Schema(implementation = ApiError.class),
                mediaType = "application/json"))
    })
    @PreAuthorize("hasAuthority('REP')")
    @GetMapping(path = "/list/stocks")
    @ResponseBody
    public ProductStockResponseDTO listStockByProductCode(
        Authentication authentication,
        @RequestParam(required = true) String productCode,
        @RequestParam(required = false, defaultValue = "C") BatchStockOrder order) {
        return this.productCatalogService.findStocksByProductCode(authentication.getName(), productCode, order);
    }


    /**
     * ================================
     * List all stocks of a product by a dueDate
     *
     * @param dayQuantity
     * @return List
     */
    @Operation(summary = "List stocks of a product that their dueDate are contained in the date interval", responses = {
        @ApiResponse(
            responseCode = "200",
            content = @Content(
                array = @ArraySchema(schema = @Schema(implementation = ProductStockResponseDTO.class)),
                mediaType = "application/json")),
        @ApiResponse(
            responseCode = "404",
            content = @Content(
                schema = @Schema(implementation = ApiError.class),
                mediaType = "application/json"))
    })
    @PreAuthorize("hasAuthority('REP')")
    @GetMapping(path = "/due-date")
    @ResponseBody
    public List<IBatchStockDueDateResponse> listStockByProductCode(@RequestParam(required = true) @Min(0) Integer dayQuantity) {
        return this.productCatalogService.findStocksByDueDate(dayQuantity);
    }

    /**
     * ================================
     * List all stocks of a product by a dueDate - Filtered by Category Type and Ordered by dueDate
     *
     * @param dayQuantity, categoryType, Order
     * @return List
     */
    @Operation(summary = "List stocks of a product that their dueDate are contained in the date interval and filtered by product category", responses = {
        @ApiResponse(
            responseCode = "200",
            content = @Content(
                array = @ArraySchema(schema = @Schema(implementation = ProductStockResponseDTO.class)),
                mediaType = "application/json")),
        @ApiResponse(
            responseCode = "404",
            content = @Content(
                schema = @Schema(implementation = ApiError.class),
                mediaType = "application/json"))
    })
    @PreAuthorize("hasAuthority('REP')")
    @GetMapping(path = "/due-date/list")
    @ResponseBody
    public List<IBatchStockDueDateResponse> listStockByProductCode(@RequestParam(required = true) @Min(0) Integer dayQuantity, @RequestParam(required = true) EProductCategory productCategory, @RequestParam(required = true, defaultValue = "asc") EResultOrder order) {
        return this.productCatalogService.findStocksByDueDateAndProductCategory(dayQuantity, productCategory, order);
    }



