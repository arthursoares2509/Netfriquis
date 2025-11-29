package com.example.netfriquis.controller;

import com.example.netfriquis.model.Filme;
import com.example.netfriquis.model.Analise;
import com.example.netfriquis.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    // Página inicial (apresentação)
    @GetMapping("/")
    public String home() {
        return "filmes/index"; // index.html
    }

    // Menu com as opções (cadastro ou análise)
    @GetMapping("/menu")
    public String menu() {
        return "filmes/menu"; // menu.html
    }

    // Página de cadastro de filmes (frontend)
    @GetMapping("/cadastro")
    public String paginaCadastro(Model model) {
        model.addAttribute("filme", new Filme());
        model.addAttribute("filmes", filmeService.listarTodos());
        return "filmes/cadastro"; // cadastro.html
    }

    // Salvar filme (backend)
    @PostMapping("/cadastro")
    public String salvarFilme(@ModelAttribute Filme filme) {
        filmeService.salvarFilme(filme);
        return "redirect:/cadastro";
    }

    // Página de análise (frontend)
    @GetMapping("/analise")
    public String paginaAnalise(Model model) {
        model.addAttribute("filmes", filmeService.listarTodos());
        model.addAttribute("analise", new Analise());
        return "filmes/analise"; // analise.html
    }

    // Adicionar análise a um filme (backend)
    @PostMapping("/analise/{id}")
    public String adicionarAnalise(
            @PathVariable Long id,
            @ModelAttribute Analise analise
    ) {
        filmeService.adicionarAnalise(id, analise);
        return "redirect:/analise";
    }

    // Remover filme (backend)
    @PostMapping("/filme/{id}/remover")
    public String removerFilme(@PathVariable Long id) {
        filmeService.removerFilme(id);
        return "redirect:/cadastro";
    }
}
