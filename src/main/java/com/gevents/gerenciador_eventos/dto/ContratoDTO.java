package com.gevents.gerenciador_eventos.dto;

import java.time.LocalDate;
import java.util.List;

import com.gevents.gerenciador_eventos.model.Modalidade;

public class ContratoDTO {
    
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private List<Modalidade> modalidades;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public LocalDate getDataInicio() {
        return dataInicio;
    }
    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }
    public LocalDate getDataFim() {
        return dataFim;
    }
    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }
    public List<Modalidade> getModalidades() {
        return modalidades;
    }
    public void setModalidades(List<Modalidade> modalidades) {
        this.modalidades = modalidades;
    }
}
