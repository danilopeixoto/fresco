package com.mercadolibre.fresco.integration;

import com.mercadolibre.fresco.service.ISessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
public class ProductCatalogControllerTest extends ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ISessionService sessionService;

}
