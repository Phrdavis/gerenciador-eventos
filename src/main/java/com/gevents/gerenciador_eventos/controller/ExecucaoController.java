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

import com.gevents.gerenciador_eventos.Service.ExecucaoService;
import com.gevents.gerenciador_eventos.dto.ExecucaoDTO;
import com.gevents.gerenciador_eventos.model.Execucao;

@RestController
@RequestMapping("/api/execucoes")
public class ExecucaoController {

    private final ExecucaoService execucaoService;

    public ExecucaoController(ExecucaoService execucaoService) {
        this.execucaoService = execucaoService;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody ExecucaoDTO execucaoDTO) {
        return execucaoService.criar(execucaoDTO);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return execucaoService.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<List<Execucao>> buscarTodos() {
        return ResponseEntity.ok(execucaoService.buscarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody ExecucaoDTO execucaoDTO) {
        return execucaoService.atualizar(id, execucaoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return execucaoService.deletar(id);
    }

    @PostMapping("/multiplos")
    public ResponseEntity<?> criarMultiplos(@RequestBody List<ExecucaoDTO> execucaoDTOs) {
        return execucaoService.criarMultiplos(execucaoDTOs);
    }

}
