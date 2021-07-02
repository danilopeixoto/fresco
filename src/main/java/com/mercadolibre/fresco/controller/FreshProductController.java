package com.mercadolibre.fresco.controller;


import com.mercadolibre.fresco.dtos.OrderDTO;
import com.mercadolibre.fresco.dtos.response.OrderResponseDTO;
import com.mercadolibre.fresco.exceptions.ApiError;
import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.exceptions.UnauthorizedException;
import com.mercadolibre.fresco.service.IFreshProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/api/v1/fresh-products")
@RestController
public class FreshProductController {
    private final IFreshProductService freshProductService;

    public FreshProductController(IFreshProductService freshProductService) {
        this.freshProductService = freshProductService;
    }

    /**
     * ================================
     * Add product to inventory
     *
     * @param token
     * @param orderDTO
     * @return OrderResponseDTO
     */
    @Operation(summary = "Add products to inventory", responses = {
            @ApiResponse(
                    responseCode = "201",
                    content = @Content(
                            schema = @Schema(implementation = OrderResponseDTO.class),
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
    @PostMapping(path = "/inboundorder", consumes = "application/json")
    @ResponseBody
    public OrderResponseDTO createOrder(
            @RequestHeader(value = "Authorization") String token, @Validated @RequestBody OrderDTO orderDTO)
            throws UnauthorizedException, NotFoundException {
        return this.freshProductService.createOrder(token, orderDTO);
    }

    /**
     * ================================
     * Add product to inventory
     *
     * @param token
     * @param orderDTO
     * @return OrderResponseDTO
     */
    @Operation(summary = "Update products from inventory", responses = {
            @ApiResponse(
                    responseCode = "201",
                    content = @Content(
                            schema = @Schema(implementation = OrderResponseDTO.class),
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
    @PutMapping(path = "/inboundorder", consumes = "application/json")
    @ResponseBody
    public OrderResponseDTO updateOrder(
            @RequestHeader(value = "Authorization") String token, @Validated @RequestBody OrderDTO orderDTO)
            throws UnauthorizedException, NotFoundException {
        return this.freshProductService.updateOrder(token, orderDTO);
    }
}
