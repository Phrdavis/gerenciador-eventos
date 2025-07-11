package com.gevents.gerenciador_eventos.dto;

import java.math.BigDecimal;

public class TecnicoDTO {
    
    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private BigDecimal diaria;

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

}
