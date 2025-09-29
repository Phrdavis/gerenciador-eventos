package com.gevents.gerenciador_eventos.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.gevents.gerenciador_eventos.dto.EventoDTO;
import com.gevents.gerenciador_eventos.model.Contrato;
import com.gevents.gerenciador_eventos.model.Evento;
import com.gevents.gerenciador_eventos.model.Modalidade;
import com.gevents.gerenciador_eventos.repository.ContratoRepository;
import com.gevents.gerenciador_eventos.repository.EventoRepository;
import com.gevents.gerenciador_eventos.repository.ModalidadeRepository;
import com.gevents.gerenciador_eventos.repository.StatusRepository;
import com.gevents.gerenciador_eventos.util.ExtrairDados;

@Service
public class EventoService {

    private EventoRepository eventoRepository;
    private StatusRepository statusRepository;
    private ContratoRepository contratoRepository;
    private ModalidadeRepository modalidadeRepository;

    public EventoService(EventoRepository eventoRepository, StatusRepository statusRepository, ContratoRepository contratoRepository, ModalidadeRepository modalidadeRepository){
        this.eventoRepository = eventoRepository;
        this.statusRepository = statusRepository;
        this.contratoRepository = contratoRepository;
        this.modalidadeRepository = modalidadeRepository;
    }
    

    public ResponseEntity<?> criar(EventoDTO evento) {

        Evento event = new Evento();

        event.setNumSolicitacao(evento.getNumSolicitacao());
        event.setNome(evento.getNome());
        event.setModelo(evento.getModelo());
        event.setUpload(evento.getUpload());
        event.setDestino(evento.getDestino());
        event.setDescricao(evento.getDescricao());
        event.setPdf(evento.getPdf());
        event.setData(evento.getData());
        event.setInicio(evento.getInicio());
        event.setFim(evento.getFim());
        event.setHoraInicio(evento.getHoraInicio());
        event.setHoraFim(evento.getHoraFim());
        event.setLocal(evento.getLocal());
        event.setResponsavel(evento.getResponsavel());
        event.setTelefoneResponsavel(evento.getTelefoneResponsavel());
        if(evento.getStatus() != null) {
            if(!statusRepository.findById(evento.getStatus().getId()).isPresent()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(java.util.Collections.singletonMap("erro", "Status não encontrado"));
            }
            event.setStatus(evento.getStatus());
        }

        if(evento.getContrato() != null) {
            if(!contratoRepository.findById(evento.getContrato().getId()).isPresent()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(java.util.Collections.singletonMap("erro", "Contrato não encontrado"));
            }
            event.setContrato(evento.getContrato());
        }

        if(evento.getModalidade() != null){

            if(!modalidadeRepository.findById(evento.getModalidade().getId()).isPresent()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(java.util.Collections.singletonMap("erro", "Modalidade não encontrada"));
            }
            event.setModalidade(evento.getModalidade());
        }

        Evento savedUser = eventoRepository.save(event);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    public ResponseEntity<?> buscarPorId(Long id) {
        Evento evento = eventoRepository.findById(id).orElse(null);
        if (evento == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Evento não encontrado"));
        }
        return ResponseEntity.ok(evento);
    }

    public List<Evento> buscarTodos() {
        return eventoRepository.findAll();
    }

    public ResponseEntity<?> atualizar(Long id, EventoDTO eventoDTO) {
        Evento evento = eventoRepository.findById(id).orElse(null);
        if (evento == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Evento não encontrado"));
        }

        if (eventoDTO.getNome() != null) {
            evento.setNome(eventoDTO.getNome());
        }
        if (eventoDTO.getModelo() != null) {
            evento.setModelo(eventoDTO.getModelo());
        }
        if (eventoDTO.getUpload() != null) {
            evento.setUpload(eventoDTO.getUpload());
        }
        if (eventoDTO.getNumSolicitacao() != null) {
            evento.setNumSolicitacao(eventoDTO.getNumSolicitacao());
        }
        if (eventoDTO.getDestino() != null) {
            evento.setDestino(eventoDTO.getDestino());
        }
        if (eventoDTO.getDescricao() != null) {
            evento.setDescricao(eventoDTO.getDescricao());
        }
        if (eventoDTO.getPdf() != null) {
            evento.setPdf(eventoDTO.getPdf());
        }
        if (eventoDTO.getData() != null) {
            evento.setData(eventoDTO.getData());
        }
        if (eventoDTO.getInicio() != null) {
            evento.setInicio(eventoDTO.getInicio());
        }
        if (eventoDTO.getFim() != null) {
            evento.setFim(eventoDTO.getFim());
        }
        if (eventoDTO.getHoraInicio() != null) {
            evento.setHoraInicio(eventoDTO.getHoraInicio());
        }
        if (eventoDTO.getHoraFim() != null) {
            evento.setHoraFim(eventoDTO.getHoraFim());
        }
        if (eventoDTO.getLocal() != null) {
            evento.setLocal(eventoDTO.getLocal());
        }
        if (eventoDTO.getResponsavel() != null) {
            evento.setResponsavel(eventoDTO.getResponsavel());
        }
        if (eventoDTO.getTelefoneResponsavel() != null) {
            evento.setTelefoneResponsavel(eventoDTO.getTelefoneResponsavel());
        }
        if (eventoDTO.getStatus() != null) {
            if (!statusRepository.findById(eventoDTO.getStatus().getId()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(java.util.Collections.singletonMap("erro", "Status não encontrado"));
            }
            evento.setStatus(eventoDTO.getStatus());
        }
        if (eventoDTO.getContrato() != null) {
            if (!contratoRepository.findById(eventoDTO.getContrato().getId()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(java.util.Collections.singletonMap("erro", "Contrato não encontrado"));
            }
            evento.setContrato(eventoDTO.getContrato());
        }
        if (eventoDTO.getModalidade() != null) {
            if (!modalidadeRepository.findById(eventoDTO.getModalidade().getId()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(java.util.Collections.singletonMap("erro", "Modalidade não encontrada"));
            }
            evento.setModalidade(eventoDTO.getModalidade());
        }
        Evento eventoAtualizado = eventoRepository.save(evento);
        return ResponseEntity.ok(eventoAtualizado);
    }

    public ResponseEntity<?> deletar(Long id) {
        if (!eventoRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(java.util.Collections.singletonMap("erro", "Evento não encontrado"));
        }
        eventoRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(java.util.Collections.singletonMap("mensagem", "Evento deletado com sucesso"));
    }

    public ResponseEntity<?> criarMultiplos(List<EventoDTO> eventos) {
        if (eventos == null || eventos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(java.util.Collections.singletonMap("erro", "Lista de eventos vazia"));
        }
        List<Evento> novosEventos = new java.util.ArrayList<>();
        for (EventoDTO dto : eventos) {
            Evento event = new Evento();
            event.setNome(dto.getNome());
            event.setModelo(dto.getModelo());
            event.setUpload(dto.getUpload());
            event.setDestino(dto.getDestino());
            event.setDescricao(dto.getDescricao());
            event.setPdf(dto.getPdf());
            event.setNumSolicitacao(dto.getNumSolicitacao());
            event.setData(dto.getData());
            event.setInicio(dto.getInicio());
            event.setFim(dto.getFim());
            event.setHoraInicio(dto.getHoraInicio());
            event.setHoraFim(dto.getHoraFim());
            event.setLocal(dto.getLocal());
            event.setResponsavel(dto.getResponsavel());
            event.setTelefoneResponsavel(dto.getTelefoneResponsavel());
            if (dto.getStatus() != null) {
                if (!statusRepository.findById(dto.getStatus().getId()).isPresent()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(java.util.Collections.singletonMap("erro", "Status não encontrado"));
                }
                event.setStatus(dto.getStatus());
            }
            if (dto.getContrato() != null) {
                if (!contratoRepository.findById(dto.getContrato().getId()).isPresent()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(java.util.Collections.singletonMap("erro", "Contrato não encontrado"));
                }
                event.setContrato(dto.getContrato());
            }
            if (dto.getModalidade() != null) {
                if (!modalidadeRepository.findById(dto.getModalidade().getId()).isPresent()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(java.util.Collections.singletonMap("erro", "Modalidade não encontrada"));
                }
                event.setModalidade(dto.getModalidade());
            }
            novosEventos.add(event);
        }
        List<Evento> salvos = eventoRepository.saveAll(novosEventos);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvos);
    }

    public ResponseEntity<?> uploadEventos(Contrato contrato, Modalidade modalidade, List<MultipartFile> arquivos) {
        
        if (arquivos == null || arquivos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(java.util.Collections.singletonMap("erro", "Por favor, envie um arquivo."));
        }

        if (contrato == null || contratoRepository.findById(contrato.getId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(java.util.Collections.singletonMap("erro", "Por favor, envie um contrato."));
        }

        if (modalidade == null || modalidadeRepository.findById(modalidade.getId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(java.util.Collections.singletonMap("erro", "Por favor, envie uma modalidade."));
        }

        String pastaDestino = Paths.get("uploads").toAbsolutePath().toString();

        File diretorio = new File(pastaDestino);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        for (MultipartFile arquivo : arquivos) {
            EventoDTO evento = new EventoDTO();
            try {
                // Definir o caminho onde cada arquivo será salvo
                String caminhoDestino = pastaDestino + File.separator + arquivo.getOriginalFilename();
                File destino = new File(caminhoDestino);

                // Salvar o arquivo
                arquivo.transferTo(destino);

                String conteudo = lerConteudoArquivo(destino);

                if (!conteudo.isEmpty()) {
                    evento = transcreverDadosUsuario(caminhoDestino);
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(java.util.Collections.singletonMap("erro", "Arquivo vazio: " + arquivo.getOriginalFilename()));
                }

                evento.setContrato(contrato);
                evento.setModalidade(modalidade);

                criar(evento);

            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(java.util.Collections.singletonMap("erro", "Erro ao salvar o arquivo " + arquivo.getOriginalFilename() + ": " + e.getMessage()));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(java.util.Collections.singletonMap("erro", e.getMessage() + ": " + arquivo.getOriginalFilename()));
            }
        }

        return ResponseEntity.ok().body(java.util.Collections.singletonMap("message", "Eventos criados!"));

    }

    private String lerConteudoArquivo(File arquivo) throws IOException {
        StringBuilder conteudo = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                conteudo.append(linha).append("\n");
            }
        }
        return conteudo.toString();
    }

    private EventoDTO transcreverDadosUsuario(String destino) throws IOException {
        
        EventoDTO dadosExtraidos = new EventoDTO();
        String textConteudo = "";

        textConteudo = ExtrairDados.extrairTextoDePDF(destino).toLowerCase();

        if (textConteudo.contains("fundação educacional")) {
            dadosExtraidos = ExtrairDados.extrairDadosFundacao(textConteudo);
        } else if (textConteudo.contains("prefeitura")) {
            dadosExtraidos = ExtrairDados.extrairDadosPrefeitura(textConteudo);
        } else {
            throw new IllegalArgumentException("Modelo não reconhecido");
        }

        dadosExtraidos.setPdf(destino);
        dadosExtraidos.setStatus(statusRepository.findByDescricao("Em Revisão"));

        return dadosExtraidos;
    }


    
}
