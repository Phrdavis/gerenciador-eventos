package com.gevents.gerenciador_eventos.util;

import java.util.List;

import com.gevents.gerenciador_eventos.dto.EventoDTO;

public class RespostaAPI {
    private List<EventoDTO> eventosCriados;
    private List<ErroExtracao> erros;

    public RespostaAPI(List<EventoDTO> eventosCriados, List<ErroExtracao> erros) {
        this.eventosCriados = eventosCriados;
        this.erros = erros;
    }

    public List<EventoDTO> getEventosCriados() {
        return eventosCriados;
    }

    public void setEventosCriados(List<EventoDTO> eventosCriados) {
        this.eventosCriados = eventosCriados;
    }

    public List<ErroExtracao> getErros() {
        return erros;
    }

    public void setErros(List<ErroExtracao> erros) {
        this.erros = erros;
    }
    
    
}
