package com.gevents.gerenciador_eventos.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Contrato {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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

        return "Contrato [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", dataInicio=" + (dataInicio != null ? dataInicio.format(dateFormatter) : "Não especificada")
                + ", dataFim=" + (dataFim != null ? dataFim.format(dateFormatter) : "Não especificada") + "]";
    }
}
