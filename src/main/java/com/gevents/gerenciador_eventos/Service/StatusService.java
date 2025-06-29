package com.gevents.gerenciador_eventos.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gevents.gerenciador_eventos.dto.StatusDTO;
import com.gevents.gerenciador_eventos.model.Status;
import com.gevents.gerenciador_eventos.repository.StatusRepository;

@Service
public class StatusService {

    private StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository){
        this.statusRepository = statusRepository;
    }


    public ResponseEntity<?> criar(StatusDTO status) {

        Status stat = new Status();
        stat.setDescricao(status.getDescricao());

        Status statusSalvo = statusRepository.save(stat);
        return ResponseEntity.status(HttpStatus.CREATED).body(statusSalvo);
    }
    public ResponseEntity<?> buscarPorId(Long id) {
        Status status = statusRepository.findById(id).orElse(null);
        if (status == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Status não encontrado"));
        }
        return ResponseEntity.ok(status);
    }
    public List<Status> buscarTodos() {
        return statusRepository.findAll();
    }
    public ResponseEntity<?> atualizar(Long id, StatusDTO statusDTO) {
        Status status = statusRepository.findById(id).orElse(null);
        if (status == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Status não encontrado"));
        }
        if(statusDTO.getDescricao() != null) {
            status.setDescricao(statusDTO.getDescricao());
        }
        Status statusAtualizado = statusRepository.save(status);

        return ResponseEntity.ok(statusAtualizado);
    }
    public ResponseEntity<?> deletar(Long id) {
        if (!statusRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Status não encontrado"));
        }
        statusRepository.deleteById(id);
        return ResponseEntity.ok("Status deletado com sucesso");
    }
    public ResponseEntity<?> criarMultiplos(List<StatusDTO> statuss) {
        if (statuss == null || statuss.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(java.util.Collections.singletonMap("erro", "Lista de statuss vazia"));
        }
        
        List<Status> statussSalvos = new ArrayList<>();
        for (StatusDTO statusDTO : statuss) {
            Status status = new Status();
            status.setDescricao(statusDTO.getDescricao());
            statussSalvos.add(status);
        }
        List<Status> salvos = statusRepository.saveAll(statussSalvos);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvos);
    }

}
