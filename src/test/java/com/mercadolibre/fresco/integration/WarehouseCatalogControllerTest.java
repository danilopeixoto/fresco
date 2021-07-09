package com.mercadolibre.fresco.integration;

import com.mercadolibre.fresco.exceptions.ApiException;
import com.mercadolibre.fresco.service.ISessionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class WarehouseCatalogControllerTest extends ControllerTest {

    private static final String URL_PATH = "/api/v1/warehouse";
    private static final String NOT_FOUND_MESSAGE = "Product TESTE not exists in warehouses!";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ISessionService sessionService;

    @Test
    void shouldPerformRequestAndRetrieveProductCount() throws Exception {
        this.mockMvc.perform(get(URL_PATH)
            .header(HttpHeaders.AUTHORIZATION, this.sessionService.login("testRep", "teste1000").getToken())
            .param("productCode", "BANANA"))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.product_id").value("BANANA"))
            .andExpect(jsonPath("$.warehouses[0].total_quantity").value(70));
    }

    @Test
    void shouldNotFindProductInWarehouses() throws Exception {
        this.mockMvc.perform(get(URL_PATH)
            .header(HttpHeaders.AUTHORIZATION, this.sessionService.login("testRep", "teste1000").getToken())
            .param("productCode", "TESTE"))
            .andDo(print()).andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ApiException))
            .andExpect(result -> Assertions.assertEquals(NOT_FOUND_MESSAGE, result.getResolvedException().getLocalizedMessage()));
    }

    @Test
    void shouldBlockUnauthorizedRequest() throws Exception {
        this.mockMvc.perform(get(URL_PATH)
            .header(HttpHeaders.AUTHORIZATION, this.sessionService.login("testBuyer", "teste1000").getToken())
            .param("productCode", "BANANA"))
            .andDo(print()).andExpect(status().isUnauthorized())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof AccessDeniedException));
    }


}
