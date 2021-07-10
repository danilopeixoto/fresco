package com.mercadolibre.fresco.integration;

import com.jayway.jsonpath.JsonPath;
import com.mercadolibre.fresco.service.ISessionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class ProductCatalogControllerTest extends ControllerTest {

    private static final String URL_PATH = "/api/v1/fresh-products";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ISessionService sessionService;

    @Test
    void shouldFindAllProducts() throws Exception {
        this.mockMvc.perform(get(URL_PATH)
            .header(HttpHeaders.AUTHORIZATION, this.sessionService.login("testBuyer", "teste1000").getToken())
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").exists())
            .andExpect(jsonPath("$").isArray());

    }

    @Test
    void shouldFindProductsByCategory() throws Exception {
        this.mockMvc.perform(get(URL_PATH)
            .header(HttpHeaders.AUTHORIZATION, this.sessionService.login("testBuyer", "teste1000").getToken())
            .param("categoryCode", "FS")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").exists())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].product_code").value("BANANA"));

    }

    @Test
    void shouldListStocksByProductCodeAndOrderByQuantity() throws Exception {
        this.mockMvc.perform(get(URL_PATH + "/list/stocks")
            .header(HttpHeaders.AUTHORIZATION, this.sessionService.login("testRep", "teste1000").getToken())
            .param("productCode", "BANANA")
            .param("order", "C")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").exists())
            .andExpect(
                result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    Integer minorAmount = Integer.parseInt(JsonPath.parse(jsonResponse).read("$['batch_stock'][0]['current_quantity']").toString());
                    Integer largeAmount = Integer.parseInt(JsonPath.parse(jsonResponse).read("$['batch_stock'][1]['current_quantity']").toString());
                    Assertions.assertTrue(minorAmount < largeAmount);
                }
            );
    }

    @Test
    void shouldListStocksByProductCodeAndOrderByDueDate() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        this.mockMvc.perform(get(URL_PATH + "/list/stocks")
            .header(HttpHeaders.AUTHORIZATION, this.sessionService.login("testRep", "teste1000").getToken())
            .param("productCode", "BANANA")
            .param("order", "F")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").exists())
            .andExpect(
                result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    LocalDate olderDate = LocalDate.parse(JsonPath.parse(jsonResponse).read("$['batch_stock'][0]['due_date']").toString(), formatter);
                    LocalDate newerDate = LocalDate.parse(JsonPath.parse(jsonResponse).read("$['batch_stock'][1]['due_date']").toString(), formatter);
                    Assertions.assertTrue(newerDate.isAfter(olderDate));
                }
            );
    }

    @Test
    void shouldListStocksByDueDateInterval() throws Exception {
        this.mockMvc.perform(get(URL_PATH + "/due-date")
            .header(HttpHeaders.AUTHORIZATION, this.sessionService.login("testRep", "teste1000").getToken())
            .param("dayQuantity", "30")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").exists())
            .andExpect(jsonPath("$.[0].product_id").value("BANANA"));
    }

    @Test
    void shouldListStocksByDueDateIntervalAndCategoryCode() throws Exception {
        this.mockMvc.perform(get(URL_PATH + "/due-date/list")
            .header(HttpHeaders.AUTHORIZATION, this.sessionService.login("testRep", "teste1000").getToken())
            .param("dayQuantity", "100")
            .param("productCategory", "FS")
            .param("order", "asc")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").exists())
            .andExpect(jsonPath("$.[0].product_type_id").value("FS"))
            .andExpect(jsonPath("$.[1].product_type_id").value("FS"));
    }

    @Test
    void shouldBlockRepUserInBuyerRequest() throws Exception {
        this.mockMvc.perform(get(URL_PATH)
            .header(HttpHeaders.AUTHORIZATION, this.sessionService.login("testRep", "teste1000").getToken())
            .param("categoryCode", "FS")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isUnauthorized())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof AccessDeniedException));
    }

    @Test
    void shouldBlockBuyerUserInRepRequest() throws Exception {
        this.mockMvc.perform(get(URL_PATH + "/due-date")
            .header(HttpHeaders.AUTHORIZATION, this.sessionService.login("testBuyer", "teste1000").getToken())
            .param("dayQuantity", "30")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isUnauthorized())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof AccessDeniedException));
    }

}
