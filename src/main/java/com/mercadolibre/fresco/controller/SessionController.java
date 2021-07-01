package com.mercadolibre.fresco.controller;

import com.mercadolibre.fresco.dtos.response.AccountResponseDTO;
import com.mercadolibre.fresco.exceptions.ApiError;
import com.mercadolibre.fresco.service.ISessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/api/v1")
@RestController
public class SessionController {
    private final ISessionService service;

    public SessionController(ISessionService sessionService) {
        this.service = sessionService;
    }

    /**
     * Validate username and password
     * If valid, it returns the account with the necessary token to send other requests.
     *
     * @param username
     * @param password
     * @return AccountResponseDTO
     * @throws NotFoundException
     */
    @Operation(summary = "Login to user account", responses = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = AccountResponseDTO.class),
                            mediaType = "application/json")),
            @ApiResponse(
                    responseCode = "401",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class),
                            mediaType = "application/json"))
    })
    @PostMapping("/sign-in")
    public AccountResponseDTO login(@RequestParam("username") String username, @RequestParam("password") String password) throws NotFoundException {
        return service.login(username, password);
    }
}
