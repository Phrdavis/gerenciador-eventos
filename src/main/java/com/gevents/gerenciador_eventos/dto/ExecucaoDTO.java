package com.gevents.gerenciador_eventos.dto;

import java.time.LocalDate;
import java.util.List;

import com.gevents.gerenciador_eventos.model.Evento;
import com.gevents.gerenciador_eventos.model.Tecnico;

public class ExecucaoDTO {
    
    private Long id;
    private String caminhoFotos;
    private LocalDate dataExecucao;
    private Long gastos;
    private String descricao;
    private Evento evento;
    private List<Tecnico> tecnicos;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Long getGastos() {
        return gastos;
    }

    public void setGastos(Long gastos) {
        this.gastos = gastos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataExecucao() {
        return dataExecucao;
    }

    public void setDataExecucao(LocalDate dataExecucao) {
        this.dataExecucao = dataExecucao;
    }

    public String getCaminhoFotos() {
        return caminhoFotos;
    }

    public void setCaminhoFotos(String caminhoFotos) {
        this.caminhoFotos = caminhoFotos;
    }

    public List<Tecnico> getTecnicos() {
        return tecnicos;
    }

    public void setTecnicos(List<Tecnico> tecnicos) {
        this.tecnicos = tecnicos;
    }

}
