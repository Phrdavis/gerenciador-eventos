package com.gevents.gerenciador_eventos.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gevents.gerenciador_eventos.dto.ExecucaoDTO;
import com.gevents.gerenciador_eventos.model.Execucao;
import com.gevents.gerenciador_eventos.model.Status;
import com.gevents.gerenciador_eventos.model.Tecnico;
import com.gevents.gerenciador_eventos.repository.EventoRepository;
import com.gevents.gerenciador_eventos.repository.ExecucaoRepository;
import com.gevents.gerenciador_eventos.repository.TecnicoRepository;

@Service
public class ExecucaoService {

    private ExecucaoRepository execucaoRepository;
    private EventoRepository eventoRepository;
    private TecnicoRepository tecnicoRepository;

    public ExecucaoService(ExecucaoRepository execucaoRepository, EventoRepository eventoRepository, TecnicoRepository tecnicoRepository){
        this.execucaoRepository = execucaoRepository;
        this.eventoRepository = eventoRepository;
        this.tecnicoRepository = tecnicoRepository;
    }


    public ResponseEntity<?> criar(ExecucaoDTO execucaoDTO) {

        Execucao execucao = new Execucao();
        execucao.setCaminhoFotos(execucaoDTO.getCaminhoFotos());
        execucao.setDataExecucao(execucaoDTO.getDataExecucao());
        execucao.setGastos(execucaoDTO.getGastos());
        execucao.setDescricao(execucaoDTO.getDescricao());

        if(!eventoRepository.existsById(execucaoDTO.getEvento().getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(java.util.Collections.singletonMap("erro", "Evento não encontrado"));
        }
        
        if (execucaoDTO.getTecnicos() != null && !execucaoDTO.getTecnicos().isEmpty()) {
            List<Tecnico> tecnicos = new ArrayList<>();
            for (Tecnico tecnico : execucaoDTO.getTecnicos()) {
                if (tecnico.getId() != null) {
                    Optional<Tecnico> tecnicoExistente = tecnicoRepository.findById(tecnico.getId());
                    if (tecnicoExistente.isPresent()) {
                        tecnicos.add(tecnicoExistente.get());
                    }
                }
            }
            execucao.setTecnicos(tecnicos);
        }

        execucao.setEvento(execucaoDTO.getEvento());

        Execucao execucaoSalvo = execucaoRepository.save(execucao);
        return ResponseEntity.status(HttpStatus.CREATED).body(execucaoSalvo);
    }
    public ResponseEntity<?> buscarPorId(Long id) {
        Execucao execucao = execucaoRepository.findById(id).orElse(null);
        if (execucao == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Execucao não encontrado"));
        }
        return ResponseEntity.ok(execucao);
    }
    public List<Execucao> buscarTodos() {
        return execucaoRepository.findByDeleted("");
    }
    public ResponseEntity<?> atualizar(Long id, ExecucaoDTO execucaoDTO) {
        Execucao execucao = execucaoRepository.findById(id).orElse(null);
        if (execucao == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Execucao não encontrado"));
        }

        if(execucaoDTO.getCaminhoFotos() != null) {
            execucao.setCaminhoFotos(execucaoDTO.getCaminhoFotos());
        }
        if(execucaoDTO.getDataExecucao() != null) {
            execucao.setDataExecucao(execucaoDTO.getDataExecucao());
        }
        if(execucaoDTO.getGastos() != null) {
            execucao.setGastos(execucaoDTO.getGastos());
        }

        if(execucaoDTO.getEvento() != null){

            if(!eventoRepository.existsById(execucaoDTO.getEvento().getId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(java.util.Collections.singletonMap("erro", "Evento não encontrado"));
            }
    
            execucao.setEvento(execucaoDTO.getEvento());

        }

        if(execucaoDTO.getDescricao() != null) {
            execucao.setDescricao(execucaoDTO.getDescricao());
        }

        if (execucaoDTO.getTecnicos() != null && !execucaoDTO.getTecnicos().isEmpty()) {
            List<Tecnico> tecnicosAtuais = execucao.getTecnicos();

            List<Tecnico> tecnicosEnviados = new ArrayList<>();
            for (Tecnico tecnico : execucaoDTO.getTecnicos()) {
                if (tecnico.getId() != null) {
                    Optional<Tecnico> tecnicoExistente = tecnicoRepository.findById(tecnico.getId());
                    if (tecnicoExistente.isPresent()) {
                        tecnicosEnviados.add(tecnicoExistente.get());
                    }
                }
            }

            List<Tecnico> tecnicosParaRemover = new ArrayList<>(tecnicosAtuais);
            tecnicosParaRemover.removeAll(tecnicosEnviados);

            List<Tecnico> tecnicosParaAdicionar = new ArrayList<>(tecnicosEnviados);
            tecnicosParaAdicionar.removeAll(tecnicosAtuais);

            for (Tecnico tecnicoParaRemover : tecnicosParaRemover) {
                execucao.getTecnicos().remove(tecnicoParaRemover);
            }

            execucao.getTecnicos().addAll(tecnicosParaAdicionar);
        }

        
        Execucao execucaoAtualizado = execucaoRepository.save(execucao);

        return ResponseEntity.ok(execucaoAtualizado);
    }

    public ResponseEntity<?> deletar(Long id) {
        Execucao execucao = execucaoRepository.findById(id).orElse(null);
        if (execucao == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Execucao não encontrado"));
        }
        execucao.setDeleted("*"); // Marca como deletado
        execucaoRepository.save(execucao);
        return ResponseEntity.ok(java.util.Collections.singletonMap("mensagem", "Execucao deletado com sucesso"));
    }

    public ResponseEntity<?> criarMultiplos(List<ExecucaoDTO> execucoes) {
        if (execucoes == null || execucoes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(java.util.Collections.singletonMap("erro", "Lista de execucoes vazia"));
        }

        List<Execucao> execucoesSalvas = new ArrayList<>();
        for (ExecucaoDTO execucaoDTO : execucoes) {
            Execucao execucao = new Execucao();
            execucao.setCaminhoFotos(execucaoDTO.getCaminhoFotos());
            execucao.setDataExecucao(execucaoDTO.getDataExecucao());
            execucao.setGastos(execucaoDTO.getGastos());
            execucao.setDescricao(execucaoDTO.getDescricao());
            
            if(!eventoRepository.existsById(execucaoDTO.getEvento().getId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(java.util.Collections.singletonMap("erro", "Evento não encontrado"));
            }
            execucao.setEvento(execucaoDTO.getEvento());

                
            if (execucaoDTO.getTecnicos() != null && !execucaoDTO.getTecnicos().isEmpty()) {
                List<Tecnico> tecnicos = new ArrayList<>();
                for (Tecnico tecnico : execucaoDTO.getTecnicos()) {
                    if (tecnico.getId() != null) {
                        Optional<Tecnico> tecnicoExistente = tecnicoRepository.findById(tecnico.getId());
                        if (tecnicoExistente.isPresent()) {
                            tecnicos.add(tecnicoExistente.get());
                        }
                    }
                }
                execucao.setTecnicos(tecnicos);
            }
            execucoesSalvas.add(execucao);
        }
        List<Execucao> salvos = execucaoRepository.saveAll(execucoesSalvas);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvos);
    }

}
