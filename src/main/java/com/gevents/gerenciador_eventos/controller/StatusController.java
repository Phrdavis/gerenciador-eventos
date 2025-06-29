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

import com.gevents.gerenciador_eventos.Service.StatusService;
import com.gevents.gerenciador_eventos.dto.StatusDTO;
import com.gevents.gerenciador_eventos.model.Status;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody StatusDTO statusDTO) {
        return statusService.criar(statusDTO);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return statusService.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<List<Status>> buscarTodos() {
        return ResponseEntity.ok(statusService.buscarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody StatusDTO statusDTO) {
        return statusService.atualizar(id, statusDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return statusService.deletar(id);
    }

    @PostMapping("/multiplos")
    public ResponseEntity<?> criarMultiplos(@RequestBody List<StatusDTO> statusDTOs) {
        return statusService.criarMultiplos(statusDTOs);
    }

}
