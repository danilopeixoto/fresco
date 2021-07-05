package com.mercadolibre.fresco.service;


import com.mercadolibre.fresco.dtos.response.AccountResponseDTO;
import javassist.NotFoundException;

public interface ISessionService {

    /**
     * Realiza la validación del usuario y contraseña ingresado.
     * En caso de ser correcto, devuelve la cuenta con el token necesario para realizar las demás consultas.
     *
     * @param username
     * @param password
     * @return AccountResponseDTO
     */
    AccountResponseDTO login(String username, String password) throws NotFoundException;
}
