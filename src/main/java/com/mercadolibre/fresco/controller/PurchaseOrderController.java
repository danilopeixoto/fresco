package com.mercadolibre.fresco.controller;


import com.mercadolibre.fresco.dtos.ProductsDTO;
import com.mercadolibre.fresco.dtos.PurchaseOrderDTO;
import com.mercadolibre.fresco.dtos.response.ProductResponseDTO;
import com.mercadolibre.fresco.dtos.response.PurchaseOrderResponseDTO;
import com.mercadolibre.fresco.exceptions.ApiError;
import com.mercadolibre.fresco.exceptions.UnauthorizedException;
import com.mercadolibre.fresco.service.IPurchaseOrderService;
import com.mercadolibre.fresco.service.ISessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Purchase orders")
@RequestMapping(path = "/api/v1/fresh-products/orders")
@RestController
public class PurchaseOrderController {
    private final IPurchaseOrderService purchaseOrderService;
    private final ISessionService sessionService;

    public PurchaseOrderController(IPurchaseOrderService purchaseOrderService, ISessionService sessionService) {
        this.purchaseOrderService = purchaseOrderService;
        this.sessionService = sessionService;
    }

    /**
     * ================================
     * Create new purchase order
     *
     * @param purchaseOrderDTO
     * @return PurchaseOrderDTO
     */
    @Operation(summary = "Create new purchase order", responses = {
        @ApiResponse(
            responseCode = "201",
            content = @Content(
                schema = @Schema(implementation = PurchaseOrderDTO.class),
                mediaType = "application/json")),
        @ApiResponse(
            responseCode = "401",
            content = @Content(
                schema = @Schema(implementation = ApiError.class),
                mediaType = "application/json"))
    })
    @PreAuthorize("hasAuthority('BUYER')")
    @PostMapping(path = "/", consumes = "application/json")
    @ResponseBody
    public PurchaseOrderResponseDTO create(Authentication authentication, @RequestBody PurchaseOrderDTO purchaseOrderDTO)
        throws UnauthorizedException {
        return this.purchaseOrderService.create(purchaseOrderDTO);
    }

    /**
     * ================================
     * Get purchase order by ID
     *
     * @param id
     * @return List
     */
    @Operation(summary = "Get purchase order products by category", responses = {
        @ApiResponse(
            responseCode = "200",
            content = @Content(
                array = @ArraySchema(schema = @Schema(implementation = ProductResponseDTO.class)),
                mediaType = "application/json")),
        @ApiResponse(
            responseCode = "401",
            content = @Content(
                array = @ArraySchema(schema = @Schema(implementation = ApiError.class)),
                mediaType = "application/json"))
    })
    @PreAuthorize("hasAuthority('BUYER')")
    @GetMapping(path = "/list")
    @ResponseBody
    public List<ProductsDTO> findById(@RequestHeader(name = "Authorization") String token, @RequestParam(required = true) Long id)
        throws UnauthorizedException {
        String username = this.sessionService.getUsername(token);

        return this.purchaseOrderService.getProductsByOrderId(username, id);
    }

    /**
     * ================================
     * Update purchase order
     *
     * @param purchaseOrderDTO
     * @return PurchaseOrderDTO
     */
    @Operation(summary = "Update purchase order by id", responses = {
        @ApiResponse(
            responseCode = "200",
            content = @Content(
                schema = @Schema(implementation = PurchaseOrderDTO.class),
                mediaType = "application/json")),
        @ApiResponse(
            responseCode = "401",
            content = @Content(
                schema = @Schema(implementation = ApiError.class),
                mediaType = "application/json")),
        @ApiResponse(
            responseCode = "404",
            content = @Content(
                schema = @Schema(implementation = ApiError.class),
                mediaType = "application/json"))
    })
    @PreAuthorize("hasAuthority('BUYER')")
    @PutMapping(path = "/", consumes = "application/json")
    @ResponseBody
    public PurchaseOrderResponseDTO updateOrder(Authentication authentication, @Validated @RequestBody PurchaseOrderDTO purchaseOrderDTO) {
        return this.purchaseOrderService.update(purchaseOrderDTO);
    }
}
