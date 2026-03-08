package com.example.netfriquis.controller;

import com.example.netfriquis.service.FilmeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class FilmeController {

    private final FilmeService filmeService;

    public FilmeController(FilmeService filmeService) {
        this.filmeService = filmeService;
    }

    @GetMapping("")
    public String index() {
        return "filmes/index";
    }

    @GetMapping("menu")
    public String menu() {
        return "filmes/menu";
    }

    @GetMapping("cadastro")
    public String cadastro(Model model) {
        model.addAttribute("filmes", filmeService.listarTodos());
        return "filmes/cadastro";
    }

    @GetMapping("analise")
    public String analise(Model model) {
        model.addAttribute("filmes", filmeService.listarTodos());
        return "filmes/analise";
    }

    @GetMapping("gerenciamento")
    public String gerenciamento(Model model) {
        model.addAttribute("filmes", filmeService.listarTodos());
        return "filmes/gerenciamento";
    }
}
