package com.example.netfriquis.repository;

import com.example.netfriquis.model.Analise;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnaliseRepository extends JpaRepository<Analise, Long> {
    List<Analise> findByFilme_Id(Long filmeId);
}
