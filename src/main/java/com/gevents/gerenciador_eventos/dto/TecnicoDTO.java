package com.gevents.gerenciador_eventos.dto;

public class TecnicoDTO {
    
    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private Long diaria;

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
    public Long getDiaria() {
        return diaria;
    }
    public void setDiaria(Long diaria) {
        this.diaria = diaria;
    }

}
