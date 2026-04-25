package com.example.netfriquis.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilmeTests {

    @Test
    void deveCriarFilmeComSucesso() {
        Filme filme = new Filme();
        filme.setId(1L);
        filme.setTitulo("O Poderoso Chefão");
        filme.setGenero("Crime");
        filme.setAnoLancamento(LocalDate.of(1972, 3, 24));

        assertNotNull(filme);
        assertEquals("O Poderoso Chefão", filme.getTitulo());
        assertEquals("Crime", filme.getGenero());
    }

    @Test
    void deveAdicionarAnaliseAoFilme() {
        Filme filme = new Filme();
        Analise analise = new Analise();
        analise.setTexto("Excelente!");

        filme.adicionarAnalise(analise);

        assertEquals(1, filme.getAnalises().size());
        assertEquals(filme, analise.getFilme());
        assertTrue(filme.getAnalises().contains(analise));
    }
}