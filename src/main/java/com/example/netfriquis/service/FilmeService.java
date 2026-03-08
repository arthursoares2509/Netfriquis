package com.example.netfriquis.service;

import com.example.netfriquis.model.Filme;
import com.example.netfriquis.repository.FilmeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class FilmeService {

    private final FilmeRepository filmeRepository;

    public FilmeService(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    public Filme salvarFilme(Filme filme) {
        return filmeRepository.save(filme);
    }

    public List<Filme> listarTodos() {
        return filmeRepository.findAll();
    }

    public Filme buscarPorId(Long id) {
        return filmeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Filme não encontrado: " + id));
    }

    @Transactional
    public void removerFilme(Long id) {
        if (!filmeRepository.existsById(id)) {
            throw new EntityNotFoundException("Filme não encontrado: " + id);
        }
        filmeRepository.deleteById(id);
    }
}
