package com.mercadolibre.fresco.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.fresco.dtos.ProductsDTO;
import com.mercadolibre.fresco.dtos.PurchaseOrderDTO;
import com.mercadolibre.fresco.exceptions.ApiException;
import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.service.ISessionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
public class PurchaseOrderControllerTest extends ControllerTest {

    private static final String URL_PATH = "/api/v1/fresh-products/orders";
    private static final Double TOTAL_PRICE = 175.;
    private static final Double UPDATED_ORDER_TOTAL_PRICE = 535.8;
    static List<ProductsDTO> productsList;
    static PurchaseOrderDTO purchaseOrderDTO;
    static List<ProductsDTO> updatedProductsList;
    static PurchaseOrderDTO updatedPurchaseOrderDto;

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
        purchaseOrderDTO = new PurchaseOrderDTO().toBuilder().id(2L)
            .date(LocalDate.now()).buyerId("testBuyer").statusCode("CANCELADO")
            .products(productsList).build();

        updatedProductsList = new ArrayList<>();
        updatedProductsList.add(new ProductsDTO().toBuilder().productId("BANANA")
            .quantity(15).build());
        updatedProductsList.add(new ProductsDTO().toBuilder().productId("SORVETE")
            .quantity(2).build());
        updatedPurchaseOrderDto = new PurchaseOrderDTO().toBuilder().id(1L)
            .date(LocalDate.now()).buyerId("newTestBuyer").statusCode("CANCELADO")
            .products(updatedProductsList).build();
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
    void shouldUpdatePurchaseOrderAndRetrieveTotalPrice() throws Exception {
        this.mockMvc.perform(put(URL_PATH + "/")
            .header(HttpHeaders.AUTHORIZATION, this.sessionService.login("testBuyer", "teste1000").getToken())
            .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(updatedPurchaseOrderDto)))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.total_price").value(UPDATED_ORDER_TOTAL_PRICE));
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
    void shouldFindOrderById() throws Exception {
        this.mockMvc.perform(get(URL_PATH + "/list")
            .header(HttpHeaders.AUTHORIZATION, this.sessionService.login("newTestBuyer", "teste1000").getToken())
            .param("id", "1"))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").exists())
            .andExpect(jsonPath("$[0].product_id").value("BANANA"))
            .andExpect(jsonPath("$[0].quantity").value("15"));
    }

    @Test
    void shouldPerformRequestAndDontFindOrder() throws Exception {
        this.mockMvc.perform(get(URL_PATH + "/list")
            .header(HttpHeaders.AUTHORIZATION, this.sessionService.login("testBuyer", "teste1000").getToken())
            .param("id", "100"))
            .andDo(print()).andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof NotFoundException));
    }

    @Test
    void shouldBlockDifferentUsers() throws Exception {
        this.mockMvc.perform(get(URL_PATH + "/list")
            .header(HttpHeaders.AUTHORIZATION, this.sessionService.login("testBuyer", "teste1000").getToken())
            .param("id", "1"))
            .andDo(print()).andExpect(status().isUnauthorized())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ApiException));
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

