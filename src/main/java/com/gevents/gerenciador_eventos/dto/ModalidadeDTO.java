package com.gevents.gerenciador_eventos.dto;

import com.gevents.gerenciador_eventos.model.Contrato;

public class ModalidadeDTO {
    
    private String nome;
    private Long valor;
    private Contrato contrato;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
}
