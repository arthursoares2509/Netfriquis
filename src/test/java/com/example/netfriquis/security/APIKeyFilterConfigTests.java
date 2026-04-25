package com.example.netfriquis.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class APIKeyFilterConfigTests {

    @Autowired
    private FilterRegistrationBean<APIKeyFilter> apiKeyFilter;

    @Test
    void contextLoads() {
        // Valida se a configuração foi lida e o filtro foi registrado como um Bean
        assertNotNull(apiKeyFilter);
    }
}