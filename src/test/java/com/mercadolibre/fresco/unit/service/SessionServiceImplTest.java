package com.mercadolibre.fresco.unit.service;

import com.mercadolibre.fresco.exceptions.ApiException;
import com.mercadolibre.fresco.repository.UserRepository;
import com.mercadolibre.fresco.service.impl.SessionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class SessionServiceImplTest {

    UserRepository repository = Mockito.mock(UserRepository.class);
    SessionServiceImpl service;

    @BeforeEach
    void setUp() {
        this.service = new SessionServiceImpl(repository);
    }


    @Test
    void loginFail() {
        when(repository.findByUsernameAndPassword("user", "invalid")).thenReturn(null);
        assertThrows(ApiException.class, () -> service.login("user", "invalid"),
                "Wrong username or password");
    }

    /* @Test
    void loginOk() {
        User userAccount = new User(null, "User", "Pass", null, null);
        when(repository.findByUsernameAndPassword("User", "Pass")).thenReturn(userAccount);
        AccountResponseDTO accountDTO = service.login("User", "Pass");
        assertEquals("User", accountDTO.getUsername());
        assertTrue(accountDTO.getToken().startsWith("Bearer"));
    } */
}
