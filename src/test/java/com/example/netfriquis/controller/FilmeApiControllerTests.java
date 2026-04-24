package com.example.netfriquis.controller; // Define o pacote do teste

import org.junit.jupiter.api.Test; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.boot.test.context.SpringBootTest; 
import static org.junit.jupiter.api.Assertions.assertNotNull; 

@SpringBootTest 
public class FilmeApiControllerTests { 

    @Autowired 
    private FilmeApiController filmeApiController; 

    @Test 
    void contextLoads() { 
        assertNotNull(filmeApiController); 
    } 
}