package com.example.netfriquis.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AnaliseRepositoryTests {

    @Autowired
    private AnaliseRepository analiseRepository;

    @Test
    void contextLoads() {
        assertNotNull(analiseRepository);
    }
}