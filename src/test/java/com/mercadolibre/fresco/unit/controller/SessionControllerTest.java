package com.mercadolibre.fresco.unit.controller;

import com.mercadolibre.fresco.controller.SessionController;
import com.mercadolibre.fresco.dtos.response.AccountResponseDTO;
import com.mercadolibre.fresco.exceptions.ApiException;
import com.mercadolibre.fresco.service.ISessionService;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class SessionControllerTest {

    SessionController controller;
    ISessionService service = Mockito.mock(ISessionService.class);

    @BeforeEach
    void setUp() throws NotFoundException {
        when(service.login("user_one", "contra12"))
            .thenThrow(new ApiException("401", "Wrong username or password", 401));
        when(service.login("user_one", "contra123"))
            .thenReturn(new AccountResponseDTO("user_one", "contra123", "TOKEN"));
        controller = new SessionController(service);
    }

    @Test
    void loginFail() throws Exception {
        assertThrows(ApiException.class, () -> controller.login("user_one", "contra12"),
            "Wrong username or password");
    }

    @Test
    void loginOk() throws Exception {
        AccountResponseDTO accountDTO = controller.login("user_one", "contra123");
        assertEquals("user_one", accountDTO.getUsername());
        assertEquals("contra123", accountDTO.getPassword());
        assertEquals("TOKEN", accountDTO.getToken());
    }
}
