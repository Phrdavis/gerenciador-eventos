package com.gevents.gerenciador_eventos.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gevents.gerenciador_eventos.dto.EventoDTO;
import com.gevents.gerenciador_eventos.dto.EventoFilterDTO;
import com.gevents.gerenciador_eventos.enums.StatusErro;
import com.gevents.gerenciador_eventos.mapper.EventoMapper;
import com.gevents.gerenciador_eventos.model.Contrato;
import com.gevents.gerenciador_eventos.model.Evento;
import com.gevents.gerenciador_eventos.model.Modalidade;
import com.gevents.gerenciador_eventos.repository.ContratoRepository;
import com.gevents.gerenciador_eventos.repository.EventoRepository;
import com.gevents.gerenciador_eventos.repository.ModalidadeRepository;
import com.gevents.gerenciador_eventos.repository.StatusRepository;
import com.gevents.gerenciador_eventos.util.ErroExtracao;
import com.gevents.gerenciador_eventos.util.ExtrairDados;
import com.gevents.gerenciador_eventos.util.RespostaAPI;

@Service
public class EventoService {

    private EventoRepository eventoRepository;
    private StatusRepository statusRepository;
    private ContratoRepository contratoRepository;
    private ModalidadeRepository modalidadeRepository;
    private final ExtrairDados extratorDeDados;

    public EventoService(EventoRepository eventoRepository, StatusRepository statusRepository, ContratoRepository contratoRepository, ModalidadeRepository modalidadeRepository){
        this.eventoRepository = eventoRepository;
        this.statusRepository = statusRepository;
        this.contratoRepository = contratoRepository;
        this.modalidadeRepository = modalidadeRepository;
        this.extratorDeDados = new ExtrairDados();
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
        event.setDataSolicitacao(evento.getDataSolicitacao());
        event.setDatas(evento.getDatas());
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
            event.setQtdModalidade(evento.getQtdModalidade());
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

    public List<Evento> buscarTodos(EventoFilterDTO eventoDTO) {
        List<Evento> eventos = eventoRepository.findByDeleted("");

            if(eventoDTO.getView() != null && eventoDTO.getView().equals("tabela")){

                // if(eventoDTO.getDatasInicio() != null){
                //     eventos = eventos.stream()
                //             .filter(e -> e.getDatasInicio() != null && !e.getDatasInicio().isBefore(eventoDTO.getDatasInicio()))
                //             .toList();
                // }
    
                // if(eventoDTO.getDatasFim() != null){
                //     eventos = eventos.stream()
                //             .filter(e -> e.getDatasFim() != null && !e.getDatasFim().isAfter(eventoDTO.getDatasFim()))
                //             .toList();
                // }

            }
            if(eventoDTO.getView() != null && eventoDTO.getView().equals("mes")){

                if(eventoDTO.getMesAtual() != 0 && eventoDTO.getAnoAtual() != 0 && eventoDTO.getMesAtual() >= 1 && eventoDTO.getMesAtual() <= 12){

                    eventos = eventos.stream()
                            .filter(e -> e.getDatas() != null && e.getDatas().stream().anyMatch(d -> d.getMonthValue() == eventoDTO.getMesAtual() && d.getYear() == eventoDTO.getAnoAtual()))
                            .toList();

                }

            }


        return eventos;
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
        if (eventoDTO.getDatas() != null) {
            evento.setDatas(eventoDTO.getDatas());
        }
        if (eventoDTO.getDataSolicitacao() != null) {
            evento.setDataSolicitacao(eventoDTO.getDataSolicitacao());
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

        // if (eventoDTO.getQtdModalidade() != 0) {

            evento.setQtdModalidade(eventoDTO.getQtdModalidade());

        // }

        Evento eventoAtualizado = eventoRepository.save(evento);
        return ResponseEntity.ok(eventoAtualizado);
    }

    public ResponseEntity<?> deletar(Long id) {
        Evento evento = eventoRepository.findById(id).orElse(null);
        if (evento == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Evento não encontrado"));
        }
        evento.setDeleted("*"); // Marca como deletado
        eventoRepository.save(evento);
        return ResponseEntity.ok(java.util.Collections.singletonMap("mensagem", "Evento deletado com sucesso"));
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
            event.setDataSolicitacao(dto.getDataSolicitacao());
            event.setDatas(dto.getDatas());
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
            event.setQtdModalidade(dto.getQtdModalidade());
            novosEventos.add(event);
        }
        List<Evento> salvos = eventoRepository.saveAll(novosEventos);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvos);
    }

    public RespostaAPI uploadEventos(Contrato contrato, Modalidade modalidade, List<MultipartFile> arquivos, String modelo) {
        
        List<Evento> eventosSalvos = new ArrayList<>();
        List<ErroExtracao> erros = new ArrayList<>();

        if (arquivos == null || arquivos.isEmpty()) {
            erros.add(new ErroExtracao("UPLOAD_VAZIO", "Por favor, envie um arquivo.", "geral", StatusErro.ERROR));
            return new RespostaAPI(new ArrayList<>(), erros);
        }

        if (contrato == null || contratoRepository.findById(contrato.getId()).isEmpty()) {
            erros.add(new ErroExtracao("CONTRATO_INVALIDO", "Por favor, envie um contrato válido.", "geral", StatusErro.ERROR));
            return new RespostaAPI(new ArrayList<>(), erros);
        }

        if (modalidade == null || modalidadeRepository.findById(modalidade.getId()).isEmpty()) {
            erros.add(new ErroExtracao("MODALIDADE_INVALIDA", "Por favor, envie uma modalidade válida.", "geral", StatusErro.ERROR));
            return new RespostaAPI(new ArrayList<>(), erros);
        }

        String pastaDestino = Paths.get("uploads").toAbsolutePath().toString();

        File diretorio = new File(pastaDestino);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        erros = extratorDeDados.getErros();
        erros.clear(); 

        for (MultipartFile arquivo : arquivos) {
            String nomeArquivo = arquivo.getOriginalFilename();
            boolean isValid = false;
            try {
                
                String caminhoDestino = pastaDestino + File.separator + arquivo.getOriginalFilename();
                File destino = new File(caminhoDestino);
                arquivo.transferTo(destino);

                // String conteudo = lerConteudoArquivo(destino);
                String conteudo = extratorDeDados.extrairTextoDePDF(caminhoDestino, nomeArquivo);

                if (conteudo == null) {
                    continue; // Se a extração falhou, o erro já foi registrado. Vá para o próximo arquivo.
                }

                EventoDTO eventoDTO = new EventoDTO();
                
                if ("fundass".equals(modelo)) {
                    eventoDTO = extratorDeDados.extrairDadosFundacao(conteudo, nomeArquivo);
                } else if ("prefeitura".equals(modelo)) {
                    eventoDTO = extratorDeDados.extrairDadosPrefeitura(conteudo, nomeArquivo);
                }
                
                if (eventoDTO.getNumSolicitacao() != null && !eventoDTO.getNumSolicitacao().isEmpty()) {
                    isValid = true;
                } else {
                    isValid = false;
                    erros.add(new ErroExtracao("FALHA_EXTRAÇÃO_CRITICA", "Número de solicitação não foi extraído, evento não será salvo.", nomeArquivo, StatusErro.ERROR));
                }
                
                if (eventoDTO.getNome() != null && !eventoDTO.getNome().isEmpty()) {
                    isValid = true;
                } else {
                    isValid = false;
                    erros.add(new ErroExtracao("FALHA_EXTRAÇÃO_CRITICA", "Nome não foi extraído, evento não será salvo.", nomeArquivo, StatusErro.ERROR));
                }
                
                if (eventoDTO.getDatas() != null && !eventoDTO.getDatas().isEmpty()) {
                    isValid = true;
                } else {
                    isValid = false;
                    erros.add(new ErroExtracao("FALHA_EXTRAÇÃO_CRITICA", "Datas não foram extraídas, evento não será salvo.", nomeArquivo, StatusErro.ERROR));
                }

                if(isValid){
                    Evento evento = EventoMapper.toEntity(eventoDTO);
                    evento.setContrato(contrato);
                    evento.setModalidade(modalidade);

                    evento.setPdf(caminhoDestino);
                    evento.setQtdModalidade(0);
                    evento.setStatus(statusRepository.findByDescricao("Em Revisão"));

                    eventosSalvos.add(eventoRepository.save(evento));
                }

            } catch (IOException e) {
                erros.add(new ErroExtracao("ERRO_IO", "Erro ao salvar o arquivo: " + e.getMessage(), nomeArquivo, StatusErro.ERROR));
            } finally {
                // Remover o arquivo temporário
                File arquivoTemp = new File(pastaDestino + File.separator + nomeArquivo);
                if (arquivoTemp.exists()) {
                    arquivoTemp.delete();
                }
            }
        }

        List<EventoDTO> eventosDTOs = EventoMapper.toDtoList(eventosSalvos);

        return new RespostaAPI(eventosDTOs, erros);

    }

    // private String lerConteudoArquivo(File arquivo) throws IOException {
    //     StringBuilder conteudo = new StringBuilder();
    //     try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
    //         String linha;
    //         while ((linha = reader.readLine()) != null) {
    //             conteudo.append(linha).append("\n");
    //         }
    //     }
    //     return conteudo.toString();
    // }

    // private EventoDTO transcreverDadosUsuario(String destino, String modelo, String nomeArquivo) throws IOException {
        
    //     EventoDTO dadosExtraidos = new EventoDTO();
    //     String textConteudo = "";

    //     textConteudo = ExtrairDados.extrairTextoDePDF(destino, nomeArquivo).toLowerCase();

    //     if (!modelo.isEmpty()){

    //         if (modelo.equals("fundass")) {
    //             dadosExtraidos = ExtrairDados.extrairDadosFundacao(textConteudo, nomeArquivo);
    //         } else if (modelo.equals("prefeitura")) {
    //             dadosExtraidos = ExtrairDados.extrairDadosPrefeitura(textConteudo, nomeArquivo);
    //         } else {
    //             throw new IllegalArgumentException("Modelo não reconhecido");
    //         }

    //     }else{

    //         if (textConteudo.contains("fundação educacional")) {
    //             dadosExtraidos = ExtrairDados.extrairDadosFundacao(textConteudo, nomeArquivo);
    //         } else if (textConteudo.contains("prefeitura")) {
    //             dadosExtraidos = ExtrairDados.extrairDadosPrefeitura(textConteudo, nomeArquivo);
    //         } else {
    //             throw new IllegalArgumentException("Modelo não reconhecido");
    //         }

    //     }


    //     return dadosExtraidos;
    // }


    
}
