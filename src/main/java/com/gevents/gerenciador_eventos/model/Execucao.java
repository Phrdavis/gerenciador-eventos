package com.gevents.gerenciador_eventos.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Execucao extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String caminhoFotos;
    private LocalDate dataExecucao;
    private Long gastos;
    private String descricao;
    
    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @ManyToMany
    @JoinTable(
        name = "execucao_tecnico",  // Nome da tabela de associação
        joinColumns = @JoinColumn(name = "execucao_id"),  // Chave estrangeira para Execucao
        inverseJoinColumns = @JoinColumn(name = "tecnico_id")  // Chave estrangeira para Tecnico
    )
    // @JsonManagedReference
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
