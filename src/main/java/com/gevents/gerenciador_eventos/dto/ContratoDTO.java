package com.gevents.gerenciador_eventos.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ContratoDTO {
    
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;

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
    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return "Contrato [nome=" + nome + ", descricao=" + descricao + ", dataInicio=" + (dataInicio != null ? dataInicio.format(dateFormatter) : "Não especificada")
                + ", dataFim=" + (dataFim != null ? dataFim.format(dateFormatter) : "Não especificada") + "]";
    }
}
