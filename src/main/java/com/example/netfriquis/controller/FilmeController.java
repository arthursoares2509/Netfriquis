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

    @GetMapping("/")
    public String home() {
        return "filmes/index";
    }

    @GetMapping("/filmes")
    public String listarFilmes(Model model) {
        model.addAttribute("filmes", filmeService.listarTodos());
        model.addAttribute("filme", new Filme());
        return "filmes/list";
    }

    @PostMapping("/filmes")
    public String salvarFilme(@ModelAttribute Filme filme) {
        filmeService.salvarFilme(filme);
        return "redirect:/filmes";
    }

    @GetMapping("/filmes/{id}")
    public String detalhesFilme(@PathVariable Long id, Model model) {
        Filme filme = filmeService.buscarPorId(id).orElse(null);
        if (filme == null) return "redirect:/filmes";
        model.addAttribute("filme", filme);
        model.addAttribute("analise", new Analise());
        return "filmes/detail";
    }

    @PostMapping("/filmes/{id}/analises")
    public String adicionarAnalise(@PathVariable Long id, @ModelAttribute Analise analise) {
        filmeService.adicionarAnalise(id, analise);
        return "redirect:/filmes/" + id;
    }
}
