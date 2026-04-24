package com.example.netfriquis.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class FilmeControllerTests {

    @Autowired
    private FilmeController filmeController;

    @Test
    void contextLoads() {
        assertNotNull(filmeController);
    }
}