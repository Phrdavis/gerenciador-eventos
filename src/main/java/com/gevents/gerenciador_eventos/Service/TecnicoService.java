package com.gevents.gerenciador_eventos.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gevents.gerenciador_eventos.dto.TecnicoDTO;
import com.gevents.gerenciador_eventos.model.Status;
import com.gevents.gerenciador_eventos.model.Tecnico;
import com.gevents.gerenciador_eventos.repository.TecnicoRepository;

@Service
public class TecnicoService {

    private TecnicoRepository tecnicoRepository;

    public TecnicoService(TecnicoRepository tecnicoRepository){
        this.tecnicoRepository = tecnicoRepository;
    }


    public ResponseEntity<?> criar(TecnicoDTO tecnico) {

        Tecnico tech = new Tecnico();
        tech.setNome(tecnico.getNome());
        tech.setTelefone(tecnico.getTelefone());
        tech.setEmail(tecnico.getEmail());
        tech.setDiaria(tecnico.getDiaria());

        Tecnico tecnicoSalvo = tecnicoRepository.save(tech);
        return ResponseEntity.status(HttpStatus.CREATED).body(tecnicoSalvo);
    }
    public ResponseEntity<?> buscarPorId(Long id) {
        Tecnico tecnico = tecnicoRepository.findById(id).orElse(null);
        if (tecnico == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Técnico não encontrado"));
        }
        return ResponseEntity.ok(tecnico);
    }
    public List<Tecnico> buscarTodos() {
        return tecnicoRepository.findByDeleted("");
    }
    public ResponseEntity<?> atualizar(Long id, TecnicoDTO tecnicoDTO) {
        Tecnico tecnico = tecnicoRepository.findById(id).orElse(null);
        if (tecnico == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Técnico não encontrado"));
        }
        if(tecnicoDTO.getNome() != null) {
            tecnico.setNome(tecnicoDTO.getNome());
        }
        if(tecnicoDTO.getTelefone() != null) {
            tecnico.setTelefone(tecnicoDTO.getTelefone());
        }
        if(tecnicoDTO.getEmail() != null) {
            tecnico.setEmail(tecnicoDTO.getEmail());
        }
        if(tecnicoDTO.getDiaria() != null) {
            tecnico.setDiaria(tecnicoDTO.getDiaria());
        }
        Tecnico tecnicoAtualizado = tecnicoRepository.save(tecnico);

        return ResponseEntity.ok(tecnicoAtualizado);
    }
    public ResponseEntity<?> deletar(Long id) {
        Tecnico tecnico = tecnicoRepository.findById(id).orElse(null);
        if (tecnico == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Técnico não encontrado"));
        }
        tecnico.setDeleted("*"); // Marca como deletado
        tecnicoRepository.save(tecnico);
        return ResponseEntity.ok(java.util.Collections.singletonMap("mensagem", "Técnico deletado com sucesso"));

    }
    public ResponseEntity<?> criarMultiplos(List<TecnicoDTO> tecnicos) {
        if (tecnicos == null || tecnicos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(java.util.Collections.singletonMap("erro", "Lista de técnicos vazia"));
        }

        List<Tecnico> tecnicosSalvos = new ArrayList<>();
        for (TecnicoDTO tecnicoDTO : tecnicos) {
            Tecnico tecnico = new Tecnico();
            tecnico.setNome(tecnicoDTO.getNome());
            tecnico.setTelefone(tecnicoDTO.getTelefone());
            tecnico.setEmail(tecnicoDTO.getEmail());
            tecnico.setDiaria(tecnicoDTO.getDiaria());
            tecnicosSalvos.add(tecnico);
        }
        List<Tecnico> salvos = tecnicoRepository.saveAll(tecnicosSalvos);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvos);
    }

}
