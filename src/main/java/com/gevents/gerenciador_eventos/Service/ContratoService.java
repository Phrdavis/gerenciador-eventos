package com.gevents.gerenciador_eventos.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gevents.gerenciador_eventos.dto.ContratoDTO;
import com.gevents.gerenciador_eventos.model.Contrato;
import com.gevents.gerenciador_eventos.model.Modalidade;
import com.gevents.gerenciador_eventos.repository.ContratoRepository;
import com.gevents.gerenciador_eventos.repository.ModalidadeRepository;

@Service
public class ContratoService {

    private ContratoRepository contratoRepository;
    private ModalidadeRepository modalidadeRepository;

    public ContratoService(ContratoRepository contratoRepository, ModalidadeRepository modalidadeRepository){
        this.contratoRepository = contratoRepository;
        this.modalidadeRepository = modalidadeRepository;
    }


    public ResponseEntity<?> criar(ContratoDTO contrato) {

        Contrato contrat = new Contrato();
        contrat.setNome(contrato.getNome());
        contrat.setDescricao(contrato.getDescricao());
        contrat.setDataInicio(contrato.getDataInicio());
        contrat.setDataFim(contrato.getDataFim());

        Contrato contratoSalvo = contratoRepository.save(contrat);

        if (contrato.getModalidades() != null) {
            for(Modalidade modalidade: contrato.getModalidades()) {
                modalidade.setContrato(contratoSalvo);
                modalidade.setNome(modalidade.getNome());
                modalidade.setValor(modalidade.getValor());
                modalidadeRepository.save(modalidade);
            }
        }
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

        if (contratoDTO.getModalidades() != null) {
             List<Modalidade> modalidadesExistentes = contratoAtualizado.getModalidades();

            Set<Long> idsModalidadesDTO = contratoDTO.getModalidades().stream()
                .map(Modalidade::getId)
                .collect(Collectors.toSet());

            for (Modalidade modalidade : modalidadesExistentes) {
                if (!idsModalidadesDTO.contains(modalidade.getId())) {
                    modalidadeRepository.delete(modalidade);
                }
            }
            
            for(Modalidade modalidade: contratoDTO.getModalidades()) {
                modalidade.setContrato(contratoAtualizado);
                modalidadeRepository.save(modalidade);
            }
        }

        return ResponseEntity.ok(contratoAtualizado);
    }
    
    @Transactional
   public ResponseEntity<?> deletar(Long id) {
        return contratoRepository.findById(id)
            .map(contrato -> {
                if (contrato.getModalidades() != null && !contrato.getModalidades().isEmpty()) {
                    modalidadeRepository.deleteAll(contrato.getModalidades());
                }
                contratoRepository.delete(contrato);
                return ResponseEntity.noContent().build(); // 204 No Content
            })
            .orElse(ResponseEntity.notFound().build());
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
