package com.gevents.gerenciador_eventos.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gevents.gerenciador_eventos.dto.ModalidadeDTO;
import com.gevents.gerenciador_eventos.model.Modalidade;
import com.gevents.gerenciador_eventos.model.Status;
import com.gevents.gerenciador_eventos.repository.ContratoRepository;
import com.gevents.gerenciador_eventos.repository.ModalidadeRepository;

@Service
public class ModalidadeService {

    private ModalidadeRepository modalidadeRepository;
    private ContratoRepository contratoRepository;

    public ModalidadeService(ModalidadeRepository modalidadeRepository, ContratoRepository contratoRepository){
        this.modalidadeRepository = modalidadeRepository;
        this.contratoRepository = contratoRepository;
    }


    public ResponseEntity<?> criar(ModalidadeDTO modalidade) {

        Modalidade modalidad = new Modalidade();
        modalidad.setNome(modalidade.getNome());
        modalidad.setValor(modalidade.getValor());

        if (!contratoRepository.existsById(modalidade.getContrato().getId())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Contrato não encontrado"));
        }
        modalidad.setContrato(modalidade.getContrato());

        Modalidade modalidadeSalvo = modalidadeRepository.save(modalidad);
        return ResponseEntity.status(HttpStatus.CREATED).body(modalidadeSalvo);
    }
    public ResponseEntity<?> buscarPorId(Long id) {
        Modalidade modalidade = modalidadeRepository.findById(id).orElse(null);
        if (modalidade == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Modalidade não encontrado"));
        }
        return ResponseEntity.ok(modalidade);
    }
    public List<Modalidade> buscarTodos() {
        return modalidadeRepository.findByDeleted("");
    }
    public ResponseEntity<?> atualizar(Long id, ModalidadeDTO modalidadeDTO) {
        Modalidade modalidade = modalidadeRepository.findById(id).orElse(null);
        if (modalidade == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Modalidade não encontrado"));
        }
        if(modalidadeDTO.getNome() != null) {
            modalidade.setNome(modalidadeDTO.getNome());
        }
        if(modalidadeDTO.getValor() != null) {
            modalidade.setValor(modalidadeDTO.getValor());
        }
        if(modalidadeDTO.getContrato() != null) {

            if (!contratoRepository.existsById(modalidadeDTO.getContrato().getId())){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Contrato não encontrado"));
            }

            modalidade.setContrato(modalidadeDTO.getContrato());
        }
        Modalidade modalidadeAtualizado = modalidadeRepository.save(modalidade);
        return ResponseEntity.ok(modalidadeAtualizado);
    }
    public ResponseEntity<?> deletar(Long id) {
        Modalidade modalidade = modalidadeRepository.findById(id).orElse(null);
        if (modalidade == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Modalidade não encontrada"));
        }
        modalidade.setDeleted("*"); // Marca como deletado
        modalidadeRepository.save(modalidade);
        return ResponseEntity.ok(java.util.Collections.singletonMap("mensagem", "Modalidade deletada com sucesso"));

    }
    public ResponseEntity<?> criarMultiplos(List<ModalidadeDTO> modalidades) {
        if (modalidades == null || modalidades.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(java.util.Collections.singletonMap("erro", "Lista de modalidades vazia"));
        }
        
        List<Modalidade> modalidadesSalvos = new ArrayList<>();
        for (ModalidadeDTO modalidadeDTO : modalidades) {
            Modalidade modalidade = new Modalidade();
            modalidade.setNome(modalidadeDTO.getNome());
            modalidade.setValor(modalidadeDTO.getValor());
            modalidade.setContrato(modalidadeDTO.getContrato());
            modalidadesSalvos.add(modalidade);
        }
        List<Modalidade> salvos = modalidadeRepository.saveAll(modalidadesSalvos);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvos);
    }

    public List<Modalidade> getModalidadesByContratoId(Long contratoId) {
        return modalidadeRepository.findByContratoId(contratoId);
    }

}
