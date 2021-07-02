package com.mercadolibre.fresco.controller;


import com.mercadolibre.fresco.dtos.InboundOrderDTO;
import com.mercadolibre.fresco.dtos.ProductsDTO;
import com.mercadolibre.fresco.dtos.response.InboundOrderResponseDTO;
import com.mercadolibre.fresco.dtos.response.ProductResponseDTO;
import com.mercadolibre.fresco.exceptions.ApiError;
import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.exceptions.UnauthorizedException;
import com.mercadolibre.fresco.service.IInboundOrderService;
import com.mercadolibre.fresco.service.crud.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Products")
@RequestMapping(path = "/api/v1/fresh-products")
@RestController
public class ProductController {
    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
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
                            mediaType = "application/json"))
    })
    @GetMapping(path = "/")
    @ResponseBody
    public List<ProductResponseDTO> listAll() {
        return this.productService.findAll();
    }

    /**
     * ================================
     * List products by category
     *
     * @param querytype
     * @return List
     */
    @Operation(summary = "List products by category", responses = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = ProductResponseDTO.class)),
                            mediaType = "application/json"))
    })
    @GetMapping(path = "/list")
    @ResponseBody
    public List<ProductResponseDTO> listByCategory(@RequestParam(required = true) String querytype) {
        return this.productService.listByCategory(querytype);
    }
}
