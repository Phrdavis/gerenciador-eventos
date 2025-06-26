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
import org.springframework.web.multipart.MultipartFile;

import com.gevents.gerenciador_eventos.Service.EventoService;
import com.gevents.gerenciador_eventos.dto.EventoDTO;
import com.gevents.gerenciador_eventos.model.Evento;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody EventoDTO eventoDTO) {
        return eventoService.criar(eventoDTO);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadEventos(@RequestBody List<MultipartFile> arquivos) {
        return eventoService.uploadEventos(arquivos);
    }
    @GetMapping("/upload")
    public ResponseEntity<?> uploadEventos() {
        return ResponseEntity.ok().body(java.util.Collections.singletonMap("message", "Endpoint para upload de eventos"));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return eventoService.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<List<Evento>> buscarTodos() {
        return ResponseEntity.ok(eventoService.buscarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody EventoDTO eventoDTO) {
        return eventoService.atualizar(id, eventoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return eventoService.deletar(id);
    }

    @PostMapping("/multiplos")
    public ResponseEntity<?> criarMultiplos(@RequestBody List<EventoDTO> usuarioDTOs) {
        return eventoService.criarMultiplos(usuarioDTOs);
    }

}
