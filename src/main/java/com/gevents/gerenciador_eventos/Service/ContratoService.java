package com.gevents.gerenciador_eventos.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gevents.gerenciador_eventos.dto.ContratoDTO;
import com.gevents.gerenciador_eventos.model.Contrato;
import com.gevents.gerenciador_eventos.repository.ContratoRepository;

@Service
public class ContratoService {

    private ContratoRepository contratoRepository;

    public ContratoService(ContratoRepository contratoRepository){
        this.contratoRepository = contratoRepository;
    }


    public ResponseEntity<?> criar(ContratoDTO contrato) {

        Contrato contrat = new Contrato();
        contrat.setNome(contrato.getNome());
        contrat.setDescricao(contrato.getDescricao());
        contrat.setDataInicio(contrato.getDataInicio());
        contrat.setDataFim(contrato.getDataFim());

        Contrato contratoSalvo = contratoRepository.save(contrat);
        return ResponseEntity.status(HttpStatus.CREATED).body(contratoSalvo);
    }
    public ResponseEntity<?> buscarPorId(Long id) {
        Contrato contrato = contratoRepository.findById(id).orElse(null);
        if (contrato == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Contrato não encontrado"));
        }
        return ResponseEntity.ok(contrato);
    }
    public List<Contrato> buscarTodos() {
        return contratoRepository.findAll();
    }
    public ResponseEntity<?> atualizar(Long id, ContratoDTO contratoDTO) {
        Contrato contrato = contratoRepository.findById(id).orElse(null);
        if (contrato == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Contrato não encontrado"));
        }
        if(contratoDTO.getNome() != null) {
            contrato.setNome(contratoDTO.getNome());
        }
        if(contratoDTO.getDescricao() != null) {
            contrato.setDescricao(contratoDTO.getDescricao());
        }
        if(contratoDTO.getDataInicio() != null) {
            contrato.setDataInicio(contratoDTO.getDataInicio());
        }
        if(contratoDTO.getDataFim() != null) {
            contrato.setDataFim(contratoDTO.getDataFim());
        }
        Contrato contratoAtualizado = contratoRepository.save(contrato);
        return ResponseEntity.ok(contratoAtualizado);
    }
    public ResponseEntity<?> deletar(Long id) {
        if (!contratoRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Contrato não encontrado"));
        }
        contratoRepository.deleteById(id);
        return ResponseEntity.ok("Contrato deletado com sucesso");
    }
    public ResponseEntity<?> criarMultiplos(List<ContratoDTO> contratos) {
        if (contratos == null || contratos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(java.util.Collections.singletonMap("erro", "Lista de contratos vazia"));
        }
        
        List<Contrato> contratosSalvos = new ArrayList<>();
        for (ContratoDTO contratoDTO : contratos) {
            Contrato contrato = new Contrato();
            contrato.setNome(contratoDTO.getNome());
            contrato.setDescricao(contratoDTO.getDescricao());
            contrato.setDataInicio(contratoDTO.getDataInicio());
            contrato.setDataFim(contratoDTO.getDataFim());
            contratosSalvos.add(contrato);
        }
        List<Contrato> salvos = contratoRepository.saveAll(contratosSalvos);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvos);
    }

}
