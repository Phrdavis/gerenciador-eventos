package com.gevents.gerenciador_eventos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gevents.gerenciador_eventos.model.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

    Status findByDescricao(String descricao);
    List<Status> findByDeleted(String deleted);

}
