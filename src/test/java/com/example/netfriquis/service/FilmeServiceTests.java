package com.example.netfriquis.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class FilmeServiceTests {

    @Autowired
    private FilmeService filmeService;

    @Test
    void contextLoads() {
        // Valida se o Service foi instanciado e se o FilmeRepository foi injetado com sucesso
        assertNotNull(filmeService);
    }
}