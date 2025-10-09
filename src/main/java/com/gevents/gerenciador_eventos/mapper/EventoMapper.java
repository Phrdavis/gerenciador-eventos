package com.gevents.gerenciador_eventos.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.gevents.gerenciador_eventos.dto.EventoDTO;
import com.gevents.gerenciador_eventos.model.Evento; // Ajuste o pacote da sua entidade

public class EventoMapper {

    /**
     * Converte um EventoDTO em uma entidade Evento.
     * @param dto O objeto EventoDTO a ser convertido.
     * @return A entidade Evento.
     */
    public static Evento toEntity(EventoDTO dto) {
        if (dto == null) {
            return null;
        }

        Evento evento = new Evento();
        // Mapeia os campos do DTO para a entidade
        evento.setNumSolicitacao(dto.getNumSolicitacao());
        evento.setDestino(dto.getDestino());
        evento.setDescricao(dto.getDescricao());
        evento.setNome(dto.getNome());
        evento.setDataSolicitacao(dto.getDataSolicitacao());
        evento.setDatas(dto.getDatas());
        evento.setHoraInicio(dto.getHoraInicio());
        evento.setHoraFim(dto.getHoraFim());
        evento.setLocal(dto.getLocal());
        evento.setResponsavel(dto.getResponsavel());
        evento.setTelefoneResponsavel(dto.getTelefoneResponsavel());
        evento.setModelo(dto.getModelo());
        evento.setUpload(dto.getUpload());
        evento.setContrato(dto.getContrato());
        evento.setModalidade(dto.getModalidade());

        return evento;
    }

    /**
     * Opcional: Converte uma entidade Evento em um EventoDTO.
     * @param evento A entidade Evento a ser convertida.
     * @return O objeto EventoDTO.
     */
    public static EventoDTO toDto(Evento evento) {
        if (evento == null) {
            return null;
        }

        EventoDTO dto = new EventoDTO();
        // Mapeia os campos da entidade para o DTO
        dto.setNumSolicitacao(evento.getNumSolicitacao());
        dto.setDestino(evento.getDestino());
        dto.setDescricao(evento.getDescricao());
        dto.setNome(evento.getNome());
        dto.setDataSolicitacao(evento.getDataSolicitacao());
        dto.setDatas(evento.getDatas());
        dto.setHoraInicio(evento.getHoraInicio());
        dto.setHoraFim(evento.getHoraFim());
        dto.setLocal(evento.getLocal());
        dto.setResponsavel(evento.getResponsavel());
        dto.setTelefoneResponsavel(evento.getTelefoneResponsavel());
        dto.setModelo(evento.getModelo());
        dto.setUpload(evento.getUpload());
        dto.setContrato(evento.getContrato());
        dto.setModalidade(evento.getModalidade());

        return dto;
    }

    public static List<EventoDTO> toDtoList(List<Evento> eventos) {
        return eventos.stream()
                      .map(EventoMapper::toDto)
                      .collect(Collectors.toList());
    }
}