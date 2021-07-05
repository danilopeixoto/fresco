package com.mercadolibre.fresco.controller;


import com.mercadolibre.fresco.dtos.response.ProductResponseDTO;
import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.model.enumeration.EProductCategory;
import com.mercadolibre.fresco.service.IProductCatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

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
  @GetMapping(path = "/")
  @ResponseBody
  public List<ProductResponseDTO> listAll() throws NotFoundException {
    return this.productCatalogService.findAll();
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
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "404",
      content = @Content(
        array = @ArraySchema(schema = @Schema(implementation = ProductResponseDTO.class)),
        mediaType = "application/json"))
  })
  @GetMapping(path = "/list")
  @ResponseBody
  public List<ProductResponseDTO> listByCategory(@RequestParam(required = true) EProductCategory category) throws NotFoundException {
    return this.productCatalogService.findProductsByCategoryCode(category);
  }
}
