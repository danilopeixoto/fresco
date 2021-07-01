package com.mercadolibre.fresco.controller;


import com.mercadolibre.fresco.dtos.CountryHouseDTO;
import com.mercadolibre.fresco.dtos.response.CountryHouseResponseDTO;
import com.mercadolibre.fresco.exceptions.ApiError;
import com.mercadolibre.fresco.exceptions.UnauthorizedException;
import com.mercadolibre.fresco.service.crud.ICountryHouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequestMapping(path = "/api/v1/fresh-products")
@RestController
public class FreshProductController {
    private IFreshProductService freshProductService;

    public FreshProductController(IFreshProductService freshProductService) {
        this.freshProductService = freshProductService;
    }

    /**================================
     * Add product to inventory
     *
     * @param token
     * @param orderDTO
     * @return ResponseEntity
     * @throws UnauthorizedException
     * */
    @Operation(summary = "Add products to inventory", responses = {
            @ApiResponse(
                    responseCode = "201",
                    content = @Content(
                            schema = @Schema(implementation = OrderDTO.class),
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
    @PostMapping(path = "/inboundorder", consumes="application/json")
    @ResponseBody
    public ResponseEntity<CountryHouseResponseDTO> createOrder(@RequestHeader(value="Authorization") String token, @Validated @RequestBody OrderDTO orderDTO) throws UnauthorizedException {

    }

    /**================================
     * Add product to inventory
     *
     * @param token
     * @param orderDTO
     * @return ResponseEntity
     * @throws UnauthorizedException
     * */
    @Operation(summary = "Add products to inventory", responses = {
            @ApiResponse(
                    responseCode = "201",
                    content = @Content(
                            schema = @Schema(implementation = StockDTO.class),
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
    @PutMapping(path = "/inboundorder", consumes="application/json")
    @ResponseBody
    public ResponseEntity<CountryHouseResponseDTO> updateOrder(@RequestHeader(value="Authorization") String token, @Validated @RequestBody OrderDTO orderDTO) throws UnauthorizedException {

    }
}
