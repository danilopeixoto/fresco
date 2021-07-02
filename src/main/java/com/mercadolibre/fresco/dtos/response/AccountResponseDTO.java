package com.mercadolibre.fresco.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDTO {
    private String username;
    private String password;
    private String token;
}