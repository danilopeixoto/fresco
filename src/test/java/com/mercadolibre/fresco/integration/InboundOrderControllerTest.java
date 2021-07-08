package com.mercadolibre.fresco.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.fresco.dtos.InboundOrderDTO;
import com.mercadolibre.fresco.dtos.SectionDTO;
import com.mercadolibre.fresco.dtos.StockDTO;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class InboundOrderControllerTest extends ControllerTest {

    private static final String URL_PATH = "/api/v1/fresh-products/inboundorder";
    static List<StockDTO> stocks;
    static InboundOrderDTO inboundOrderDTO;
    static List<StockDTO> updatedStocks;
    static InboundOrderDTO updatedInboundOrder;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ISessionService sessionService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeAll
    static void setup() {
        stocks = new ArrayList<>();
        stocks.add(new StockDTO().toBuilder().batchNumber(100).price(50.).currentTemperature(15.)
        .dueDate(LocalDate.now().plusWeeks(10)).productCode("BANANA").manufacturingDate(LocalDate.now())
        .manufacturingTime(LocalTime.now()).currentQuantity(30).initialQuantity(50).build());
        inboundOrderDTO = new InboundOrderDTO().toBuilder().orderDate(LocalDate.now()).orderNumber(1L).batchStock(stocks)
            .section(new SectionDTO().toBuilder().sectionCode("FS").warehouseCode("WAREHOUSE_TESTE").build()).build();

        updatedStocks = new ArrayList<>();
        updatedStocks.add(new StockDTO().toBuilder().batchNumber(100).price(50.).currentTemperature(10.)
            .dueDate(LocalDate.now().plusWeeks(10)).productCode("BANANA").manufacturingDate(LocalDate.now())
            .manufacturingTime(LocalTime.now()).currentQuantity(10).initialQuantity(50).build());
        updatedInboundOrder = new InboundOrderDTO().toBuilder().orderDate(LocalDate.now()).orderNumber(1L).batchStock(updatedStocks)
            .section(new SectionDTO().toBuilder().sectionCode("FS").warehouseCode("WAREHOUSE_TESTE").build()).build();
    }

    @Test
    void shouldPerformCreateRequest() throws Exception {
        this.mockMvc.perform(post(URL_PATH)
            .header(HttpHeaders.AUTHORIZATION, this.sessionService.login("testRep", "teste1000").getToken())
            .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(inboundOrderDTO)))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.batch_stock[0].batch_number").value(100));
    }

    @Test
    void shouldPerformUpdateRequest() throws Exception {
        this.mockMvc.perform(put(URL_PATH)
            .header(HttpHeaders.AUTHORIZATION, this.sessionService.login("testRep", "teste1000").getToken())
            .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(updatedInboundOrder)))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.batch_stock[0].batch_number").value(100))
            .andExpect(jsonPath("$.batch_stock[0].current_temperature").value(10.))
            .andExpect(jsonPath("$.batch_stock[0].current_quantity").value(10));
    }

    @Test
    void shouldBlockUnauthorizedRequest() throws Exception {
        this.mockMvc.perform(post(URL_PATH)
            .header(HttpHeaders.AUTHORIZATION, this.sessionService.login("testBuyer", "teste1000").getToken())
            .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(inboundOrderDTO)))
            .andDo(print()).andExpect(status().isUnauthorized())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof AccessDeniedException));    }


}
