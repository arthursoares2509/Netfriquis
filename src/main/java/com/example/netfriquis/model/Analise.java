package com.example.netfriquis.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Analise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String texto;

    @Column(nullable = false)
    private Integer nota;

    @ManyToOne
    @JoinColumn(name = "filme_id", nullable = false)
    @JsonBackReference
    private Filme filme;

    public Analise() {}

    public Analise(Long id, String texto, Integer nota, Filme filme) {
        this.id = id;
        this.texto = texto;
        this.nota = nota;
        this.filme = filme;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public Integer getNota() { return nota; }
    public void setNota(Integer nota) { this.nota = nota; }
    public Filme getFilme() { return filme; }
    public void setFilme(Filme filme) { this.filme = filme; }
}
