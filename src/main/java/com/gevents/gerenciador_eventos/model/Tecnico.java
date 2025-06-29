package com.gevents.gerenciador_eventos.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Tecnico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private BigDecimal diaria;
    
    @ManyToMany(mappedBy = "tecnicos")
    @JsonBackReference
    private List<Execucao> execucoes;

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
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public BigDecimal getDiaria() {
        return diaria;
    }
    public void setDiaria(BigDecimal diaria) {
        this.diaria = diaria;
    }

    public List<Execucao> getExecucoes() {
        return execucoes;
    }

    public void setExecucoes(List<Execucao> execucoes) {
        this.execucoes = execucoes;
    }

}
