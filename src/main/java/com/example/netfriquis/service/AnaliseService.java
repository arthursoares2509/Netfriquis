package com.example.netfriquis.service;

import com.example.netfriquis.model.Analise;
import com.example.netfriquis.model.Filme;
import com.example.netfriquis.repository.AnaliseRepository;
import com.example.netfriquis.repository.FilmeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AnaliseService {

    private final AnaliseRepository analiseRepository;
    private final FilmeRepository filmeRepository;

    public AnaliseService(AnaliseRepository analiseRepository, FilmeRepository filmeRepository) {
        this.analiseRepository = analiseRepository;
        this.filmeRepository = filmeRepository;
    }

    @Transactional
    public Analise adicionarAnalise(Long filmeId, Analise analise) {
        Filme filme = filmeRepository.findById(filmeId)
                .orElseThrow(() -> new EntityNotFoundException("Filme não encontrado: " + filmeId));

        analise.setFilme(filme);
        return analiseRepository.save(analise);
    }

    @Transactional
    public Analise atualizarAnalise(Long analiseId, Analise analiseAtualizada) {
        Analise analiseExistente = analiseRepository.findById(analiseId)
                .orElseThrow(() -> new EntityNotFoundException("Análise não encontrada: " + analiseId));

        analiseExistente.setTexto(analiseAtualizada.getTexto());
        analiseExistente.setNota(analiseAtualizada.getNota());

        return analiseRepository.save(analiseExistente);
    }

    @Transactional
    public void removerAnalise(Long analiseId) {
        if (!analiseRepository.existsById(analiseId)) {
            throw new EntityNotFoundException("Análise não encontrada: " + analiseId);
        }
        analiseRepository.deleteById(analiseId);
    }

    public List<Analise> listarPorFilme(Long filmeId) {
        return analiseRepository.findByFilme_Id(filmeId);
    }
}
