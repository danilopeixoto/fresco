package com.mercadolibre.fresco.controller;


import com.mercadolibre.fresco.dtos.CountryHouseDTO;
import com.mercadolibre.fresco.dtos.response.CountryHouseResponseDTO;
import com.mercadolibre.fresco.dtos.response.InboundOrderResponseDTO;
import com.mercadolibre.fresco.exceptions.ApiError;
import com.mercadolibre.fresco.service.crud.ICountryHouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Country houses")
@RequestMapping(path = "/api/v1/countries")
@RestController
public class CountryHouseController {
  private ICountryHouseService countryHouseService;

  public CountryHouseController(ICountryHouseService countryHouseService) {
    this.countryHouseService = countryHouseService;
  }

  /**
   * ================================
   * Create country house
   *
   * @param newCountryHouse
   * @return ResponseEntity
   */
  @Operation(summary = "Create country house", responses = {
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
        mediaType = "application/json"))
  })
  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping(path = "/", consumes = "application/json")
  @ResponseBody
  public ResponseEntity<CountryHouseResponseDTO> create(@Validated @RequestBody CountryHouseDTO newCountryHouse) {
    CountryHouseDTO countryHouseDTO = countryHouseService.create(newCountryHouse);

    CountryHouseResponseDTO response = new CountryHouseResponseDTO();

    if (countryHouseDTO != null) {
      response.setCountryHouse(countryHouseDTO);
      response.setMessage("Country created");
      URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(countryHouseDTO.getId()).toUri();
      return new ResponseEntity<CountryHouseResponseDTO>(response, HttpStatus.CREATED).created(location).body(response);
    } else {
      response.setCountryHouse(newCountryHouse);
      response.setMessage("Country already exists");
      return new ResponseEntity<CountryHouseResponseDTO>(response, HttpStatus.BAD_REQUEST);
    }
  }
}
