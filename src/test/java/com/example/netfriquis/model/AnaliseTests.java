package com.example.netfriquis.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AnaliseTests {

    @Test
    void deveCriarAnaliseComSucesso() {
        Analise analise = new Analise();
        analise.setId(1L);
        analise.setTexto("Filme muito bom!");
        analise.setNota(5);

        assertNotNull(analise);
        assertEquals(1L, analise.getId());
        assertEquals("Filme muito bom!", analise.getTexto());
        assertEquals(5, analise.getNota());
    }
}