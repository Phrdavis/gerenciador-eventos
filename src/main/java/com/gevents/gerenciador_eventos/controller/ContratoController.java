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

import com.gevents.gerenciador_eventos.Service.ContratoService;
import com.gevents.gerenciador_eventos.dto.ContratoDTO;
import com.gevents.gerenciador_eventos.model.Contrato;

@RestController
@RequestMapping("/api/contratos")
public class ContratoController {

    private final ContratoService contratoService;

    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody ContratoDTO contratoDTO) {
        return contratoService.criar(contratoDTO);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return contratoService.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<List<Contrato>> buscarTodos() {
        return ResponseEntity.ok(contratoService.buscarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody ContratoDTO contratoDTO) {
        return contratoService.atualizar(id, contratoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return contratoService.deletar(id);
    }

    @PostMapping("/multiplos")
    public ResponseEntity<?> criarMultiplos(@RequestBody List<ContratoDTO> contratoDTOs) {
        return contratoService.criarMultiplos(contratoDTOs);
    }

}
