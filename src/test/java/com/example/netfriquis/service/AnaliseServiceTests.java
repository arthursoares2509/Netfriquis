package com.example.netfriquis.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AnaliseServiceTests {

    @Autowired
    private AnaliseService analiseService;

    @Test
    void contextLoads() {
        // Valida se o Service foi criado e se os Repositories foram injetados corretamente
        assertNotNull(analiseService);
    }
}