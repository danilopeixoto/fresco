package com.mercadolibre.fresco.integration;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GenerateDoc extends ControllerTest {

    /**
     * Integration test to generate swagger.yaml file for fury docs [http://furydocs.io/documentation-service]
     *
     * @throws IOException
     */
    @Test
    void generateSwaggerDocumentation() throws IOException {
        File outputDir = new File("docs/specs");
        ResponseEntity<String> responseEntity = this.testRestTemplate.getForEntity("/docs/v1/openapi", String.class);
        assertTrue(responseEntity.getStatusCode()
            .is2xxSuccessful());
        assertNotNull(responseEntity.getBody());

        outputDir.mkdirs();
        Files.writeString(Paths.get(outputDir.getAbsolutePath() + "/swagger.yaml"), responseEntity.getBody());
    }
}
