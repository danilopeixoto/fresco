package com.mercadolibre.fresco.controller;

import com.mercadolibre.fresco.dtos.response.AccountResponseDTO;
import com.mercadolibre.fresco.exceptions.ApiError;
import com.newrelic.api.agent.NewRelic;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    /**
     * Health check status
     *
     * @return String
     */
    @Operation(summary = "Check API health", responses = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = AccountResponseDTO.class),
                            mediaType = "application/json")),
            @ApiResponse(
                    responseCode = "500",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class),
                            mediaType = "application/json"))
    })
    @GetMapping("/ping")
    public String ping() {
        NewRelic.ignoreTransaction();
        return "pong";
    }
}
