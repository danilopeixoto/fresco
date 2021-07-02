package com.mercadolibre.fresco.controller;


import com.mercadolibre.fresco.dtos.InboundOrderDTO;
import com.mercadolibre.fresco.dtos.response.InboundOrderResponseDTO;
import com.mercadolibre.fresco.exceptions.ApiError;
import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.exceptions.UnauthorizedException;
import com.mercadolibre.fresco.service.IInboundOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/api/v1/fresh-products/inboundorder")
@RestController
public class InboundOrderController {
    private final IInboundOrderService inboundOrderService;

    public InboundOrderController(IInboundOrderService inboundOrderService) {
        this.inboundOrderService = inboundOrderService;
    }

    /**
     * ================================
     * Add products to inventory
     *
     * @param inboundOrderDTO
     * @return InboundOrderResponseDTO
     */
    @Operation(summary = "Add products to inventory", responses = {
            @ApiResponse(
                    responseCode = "201",
                    content = @Content(
                            schema = @Schema(implementation = InboundOrderResponseDTO.class),
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
    @PreAuthorize("hasRole('REP')")
    @PostMapping(path = "/", consumes = "application/json")
    @ResponseBody
    public InboundOrderResponseDTO createOrder(@Validated @RequestBody InboundOrderDTO inboundOrderDTO) throws UnauthorizedException, NotFoundException {
        return this.inboundOrderService.create(inboundOrderDTO);
    }

    /**
     * ================================
     * Update products from inventory
     *
     * @param inboundOrderDTO
     * @return InboundOrderResponseDTO
     */
    @Operation(summary = "Update products from inventory", responses = {
            @ApiResponse(
                    responseCode = "201",
                    content = @Content(
                            schema = @Schema(implementation = InboundOrderResponseDTO.class),
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
    @PreAuthorize("hasRole('REP')")
    @PutMapping(path = "/", consumes = "application/json")
    @ResponseBody
    public InboundOrderResponseDTO updateOrder(@Validated @RequestBody InboundOrderDTO inboundOrderDTO) throws UnauthorizedException, NotFoundException {
        return this.inboundOrderService.update(inboundOrderDTO);
    }
}
