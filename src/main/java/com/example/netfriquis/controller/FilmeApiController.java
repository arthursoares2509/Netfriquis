package com.example.netfriquis.controller;

import com.example.netfriquis.model.Filme;
import com.example.netfriquis.model.Analise;
import com.example.netfriquis.service.FilmeService;
import com.example.netfriquis.service.AnaliseService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/filmes")
@CrossOrigin(origins = "*")
public class FilmeApiController {

    private final FilmeService filmeService;
    private final AnaliseService analiseService;

    public FilmeApiController(FilmeService filmeService, AnaliseService analiseService) {
        this.filmeService = filmeService;
        this.analiseService = analiseService;
    }

    // -------------------- FILMES -------------------- //

    @GetMapping
    public List<Filme> listarFilmes() {
        return filmeService.listarTodos();
    }

    @GetMapping("/{id}")
    public Filme buscarFilme(@PathVariable Long id) {
        return filmeService.buscarPorId(id);
    }

    @PostMapping
    public Filme criarFilme(@RequestBody Filme filme) {
        return filmeService.salvarFilme(filme);
    }

    @PutMapping("/{id}")
    public Filme atualizarFilme(@PathVariable Long id, @RequestBody Filme filmeAtualizado) {
        Filme filmeExistente = filmeService.buscarPorId(id);

        filmeExistente.setTitulo(filmeAtualizado.getTitulo());
        filmeExistente.setSinopse(filmeAtualizado.getSinopse());
        filmeExistente.setGenero(filmeAtualizado.getGenero());
        filmeExistente.setAnoLancamento(filmeAtualizado.getAnoLancamento());

        return filmeService.salvarFilme(filmeExistente);
    }

    @DeleteMapping("/{id}")
    public void removerFilme(@PathVariable Long id) {
        filmeService.removerFilme(id);
    }

    // -------------------- ANÁLISES -------------------- //

    @GetMapping("/{filmeId}/analises")
    public List<Analise> listarAnalises(@PathVariable Long filmeId) {
        return analiseService.listarPorFilme(filmeId);
    }

    @PostMapping("/{filmeId}/analises")
    public Analise adicionarAnalise(@PathVariable Long filmeId, @RequestBody Analise analise) {
        return analiseService.adicionarAnalise(filmeId, analise);
    }

    @PutMapping("/analises/{analiseId}")
        public Analise atualizarAnalise(@PathVariable Long analiseId, @RequestBody Analise analiseAtualizada) {
        return analiseService.atualizarAnalise(analiseId, analiseAtualizada);
    }

    @DeleteMapping("/analises/{analiseId}")
    public void removerAnalise(@PathVariable Long analiseId) {
        analiseService.removerAnalise(analiseId);
    }
}
