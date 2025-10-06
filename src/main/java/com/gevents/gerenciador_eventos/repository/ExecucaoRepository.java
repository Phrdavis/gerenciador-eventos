package com.gevents.gerenciador_eventos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gevents.gerenciador_eventos.model.Execucao;

@Repository
public interface ExecucaoRepository extends JpaRepository<Execucao, Long> {
    
    List<Execucao> findByDeleted(String deleted);

}
