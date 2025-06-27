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

import com.gevents.gerenciador_eventos.Service.ModalidadeService;
import com.gevents.gerenciador_eventos.dto.ModalidadeDTO;
import com.gevents.gerenciador_eventos.model.Modalidade;

@RestController
@RequestMapping("/api/modalidades")
public class ModalidadeController {

    private final ModalidadeService modalidadeService;

    public ModalidadeController(ModalidadeService modalidadeService) {
        this.modalidadeService = modalidadeService;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody ModalidadeDTO modalidadeDTO) {
        return modalidadeService.criar(modalidadeDTO);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return modalidadeService.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<List<Modalidade>> buscarTodos() {
        return ResponseEntity.ok(modalidadeService.buscarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody ModalidadeDTO modalidadeDTO) {
        return modalidadeService.atualizar(id, modalidadeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return modalidadeService.deletar(id);
    }

    @PostMapping("/multiplos")
    public ResponseEntity<?> criarMultiplos(@RequestBody List<ModalidadeDTO> modalidadeDTOs) {
        return modalidadeService.criarMultiplos(modalidadeDTOs);
    }
    
    @GetMapping("/contrato/{contratoId}")
    public List<Modalidade> getModalidadesByContratoId(@PathVariable Long contratoId) {
        return modalidadeService.getModalidadesByContratoId(contratoId);
    }

}
