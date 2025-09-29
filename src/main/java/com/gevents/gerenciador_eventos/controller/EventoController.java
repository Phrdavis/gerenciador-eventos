package com.gevents.gerenciador_eventos.controller;

import java.io.File;
import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gevents.gerenciador_eventos.Service.EventoService;
import com.gevents.gerenciador_eventos.dto.EventoDTO;
import com.gevents.gerenciador_eventos.model.Contrato;
import com.gevents.gerenciador_eventos.model.Evento;
import com.gevents.gerenciador_eventos.model.Modalidade;

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
    public ResponseEntity<?> uploadEventos(
        @RequestPart("contrato") Contrato contrato,
        @RequestPart("modalidade") Modalidade modalidade,
        @RequestPart("arquivos") List<MultipartFile> arquivos
    ) {
        return eventoService.uploadEventos(contrato, modalidade, arquivos);
    }
    @GetMapping("/upload")
    public ResponseEntity<?> uploadEventos(@RequestParam String path) {
        File file = new File(path);

        if (!file.exists() || !file.getName().toLowerCase().endsWith(".pdf")) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
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
