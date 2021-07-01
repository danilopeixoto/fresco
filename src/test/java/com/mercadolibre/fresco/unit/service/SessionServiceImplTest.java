package com.mercadolibre.fresco.unit.service;

import com.mercadolibre.fresco.dtos.response.AccountResponseDTO;
import com.mercadolibre.fresco.exceptions.ApiException;
import com.mercadolibre.fresco.model.Account;
import com.mercadolibre.fresco.repository.AccountRepository;
import com.mercadolibre.fresco.service.impl.SessionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SessionServiceImplTest {

    AccountRepository repository = Mockito.mock(AccountRepository.class);
    SessionServiceImpl service;

    @BeforeEach
    void setUp(){
        this.service = new SessionServiceImpl(repository);
    }


    @Test
    void loginFail() {
        when(repository.findByUsernameAndPassword("user", "invalid")).thenReturn(null);
        assertThrows(ApiException.class, () -> service.login("user", "invalid"),
                "Usuario y/o contraseña incorrecto");
    }

    @Test
    void loginOk(){
        Account account = new Account(null, "User", "Pass", null, null);
        when(repository.findByUsernameAndPassword("User", "Pass")).thenReturn(account);
        AccountResponseDTO accountDTO = service.login("User","Pass");
        assertEquals("User", accountDTO.getUsername());
        assertTrue(accountDTO.getToken().startsWith("Bearer"));
    }
}
