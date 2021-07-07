package com.mercadolibre.fresco.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.fresco.dtos.ProductsDTO;
import com.mercadolibre.fresco.dtos.PurchaseOrderDTO;
import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.repository.PurchaseOrderRepository;
import com.mercadolibre.fresco.service.ISessionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class PurchaseOrderControllerTest extends ControllerTest{

    private static final String URL_PATH = "/api/v1/fresh-products/orders";
    private static final Double TOTAL_PRICE = 175.;
    static List<ProductsDTO> productsList;
    static PurchaseOrderDTO purchaseOrderDTO;

    @MockBean
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    ISessionService sessionService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeAll
    static void setup() {
        productsList = new ArrayList<>();
        productsList.add(new ProductsDTO().toBuilder().productId("BANANA")
            .quantity(5).build());
        purchaseOrderDTO = new PurchaseOrderDTO().toBuilder().id(1L)
            .date(LocalDate.now()).buyerId("testBuyer").statusCode("CANCELADO")
            .products(productsList).build();
    }

    @Test
    void shouldCreatePurchaseOrderAndRetrieveTotalPrice() throws Exception {
        this.mockMvc.perform(post(URL_PATH + "/")
            .header(HttpHeaders.AUTHORIZATION, this.sessionService.login("testBuyer", "teste1000").getToken())
            .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(purchaseOrderDTO)))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.total_price").value(TOTAL_PRICE));
    }

    @Test
    void shouldDontFindPurchaseOrderToUpdate() throws Exception {
        this.mockMvc.perform(put(URL_PATH + "/")
            .header(HttpHeaders.AUTHORIZATION, this.sessionService.login("testBuyer", "teste1000").getToken())
            .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(purchaseOrderDTO)))
            .andDo(print()).andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof NotFoundException));

    }

    @Test
    void shouldPerformRequestAndDontFindOrder() throws Exception {
        this.mockMvc.perform(get(URL_PATH + "/list")
            .header(HttpHeaders.AUTHORIZATION, this.sessionService.login("testBuyer", "teste1000").getToken())
            .param("id", String.valueOf(1L)))
            .andDo(print()).andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof NotFoundException));
    }

    @Test
    void shouldBlockUnauthorizedUser() throws Exception {
        this.mockMvc.perform(post(URL_PATH + "/")
            .header(HttpHeaders.AUTHORIZATION, this.sessionService.login("testRep", "teste1000").getToken())
            .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(purchaseOrderDTO)))
            .andDo(print()).andExpect(status().isUnauthorized())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof AccessDeniedException));
    }

}
