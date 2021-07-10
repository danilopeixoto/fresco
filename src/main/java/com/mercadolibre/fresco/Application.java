package com.mercadolibre.fresco;

import com.mercadolibre.fresco.config.SpringConfig;
import com.mercadolibre.fresco.util.ScopeUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ScopeUtils.calculateScopeSuffix();
        new SpringApplicationBuilder(SpringConfig.class).registerShutdownHook(true)
            .run(args);
    }
}
