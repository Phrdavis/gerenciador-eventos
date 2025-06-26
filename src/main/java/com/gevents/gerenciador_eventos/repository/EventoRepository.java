package com.gevents.gerenciador_eventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gevents.gerenciador_eventos.model.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    
}
