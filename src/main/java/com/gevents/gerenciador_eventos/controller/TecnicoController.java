package com.gevents.gerenciador_eventos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gevents.gerenciador_eventos.Service.TecnicoService;
import com.gevents.gerenciador_eventos.dto.TecnicoDTO;
import com.gevents.gerenciador_eventos.model.Tecnico;

@RestController
@RequestMapping("/api/tecnicos")
public class TecnicoController {

    private final TecnicoService tecnicoService;

    public TecnicoController(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody TecnicoDTO tecnicoDTO) {
        return tecnicoService.criar(tecnicoDTO);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return tecnicoService.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<List<Tecnico>> buscarTodos() {
        return ResponseEntity.ok(tecnicoService.buscarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody TecnicoDTO tecnicoDTO) {
        return tecnicoService.atualizar(id, tecnicoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return tecnicoService.deletar(id);
    }

    @PostMapping("/multiplos")
    public ResponseEntity<?> criarMultiplos(@RequestBody List<TecnicoDTO> tecnicoDTOs) {
        return tecnicoService.criarMultiplos(tecnicoDTOs);
    }

}
