package com.gevents.gerenciador_eventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gevents.gerenciador_eventos.model.Modalidade;

@Repository
public interface ModalidadeRepository extends JpaRepository<Modalidade, Long> {
    
}
