package com.mercadolibre.fresco.controller;


import com.mercadolibre.fresco.dtos.InboundOrderDTO;
import com.mercadolibre.fresco.dtos.response.InboundOrderResponseDTO;
import com.mercadolibre.fresco.exceptions.ApiError;
import com.mercadolibre.fresco.exceptions.BadRequestException;
import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.exceptions.UnauthorizedException;
import com.mercadolibre.fresco.service.IInboundOrderService;
import com.mercadolibre.fresco.service.ISessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Warehouse orders")
@RequestMapping(path = "/api/v1/fresh-products/inboundorder")
@RestController
public class InboundOrderController {

    private final IInboundOrderService inboundOrderService;
    private final ISessionService sessionService;

    public InboundOrderController(IInboundOrderService inboundOrderService, ISessionService sessionService) {
        this.inboundOrderService = inboundOrderService;
        this.sessionService = sessionService;
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
            responseCode = "400",
            content = @Content(
                schema = @Schema(implementation = ApiError.class),
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
    @PreAuthorize("hasAuthority('REP')")
    @PostMapping(consumes = "application/json")
    @ResponseBody
    public InboundOrderResponseDTO create(@RequestHeader(name = "Authorization") String token, @Validated @RequestBody InboundOrderDTO inboundOrderDTO)
        throws UnauthorizedException, NotFoundException, BadRequestException {
        String username = this.sessionService.getUsername(token);

        return this.inboundOrderService.create(username, inboundOrderDTO);
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
            responseCode = "400",
            content = @Content(
                schema = @Schema(implementation = ApiError.class),
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
    @PreAuthorize("hasAuthority('REP')")
    @PutMapping(consumes = "application/json")
    @ResponseBody
    public InboundOrderResponseDTO update(@RequestHeader(name = "Authorization") String token, @Validated @RequestBody InboundOrderDTO inboundOrderDTO)
        throws UnauthorizedException, NotFoundException, BadRequestException {

        String username = this.sessionService.getUsername(token);

        return this.inboundOrderService.update(username, inboundOrderDTO);
    }
}
