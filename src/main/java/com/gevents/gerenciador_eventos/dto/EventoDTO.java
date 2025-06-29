package com.gevents.gerenciador_eventos.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.gevents.gerenciador_eventos.model.Contrato;
import com.gevents.gerenciador_eventos.model.Status;

public class EventoDTO {

    private String nome; 
    private String modelo;
    private String upload;
    private String numSolicitacao;
    private String destino;
    private String descricao;
    private String pdf;
    private LocalDate data;
    private LocalDate inicio;
    private LocalDate fim;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private String local;
    private String responsavel;
    private String telefoneResponsavel;
    private Status status;
    private Contrato contrato;
    private Contrato modalidade;

    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getModelo(){
        return modelo;
    }
    public void setModelo(String modelo){
        this.modelo = modelo;
    }
    public String getUpload(){
        return upload;
    }
    public void setUpload(String upload){
        this.upload = upload;
    }
    public String getNumSolicitacao(){
        return numSolicitacao;
    }
    public void setNumSolicitacao(String numSolicitacao){
        this.numSolicitacao = numSolicitacao;
    }
    public String getDestino(){
        return destino;
    }
    public void setDestino(String destino){
        this.destino = destino;
    }
    public String getDescricao(){
        return descricao;
    }
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
    public String getPdf(){
        return pdf;
    }
    public void setPdf(String pdf){
        this.pdf = pdf;
    }
    public LocalDate  getData(){
        return data;
    }
    public void setData(LocalDate  data){
        this.data = data;
    }
    public LocalDate  getInicio(){
        return inicio;
    }
    public void setInicio(LocalDate  inicio){
        this.inicio = inicio;
    }
    public LocalDate  getFim(){
        return fim;
    }
    public void setFim(LocalDate  fim){
        this.fim = fim;
    }
    public String getLocal(){
        return local;
    }
    public void setLocal(String local){
        this.local = local;
    }
    public String getResponsavel(){
        return responsavel;
    }
    public void setResponsavel(String responsavel){
        this.responsavel = responsavel;
    }
    public String getTelefoneResponsavel(){
        return telefoneResponsavel;
    }
    public void setTelefoneResponsavel(String telefoneResponsavel){
        this.telefoneResponsavel = telefoneResponsavel;
    }
    public LocalTime getHoraInicio(){
        return horaInicio;
    }
    public void setHoraInicio(LocalTime horaInicio){
        this.horaInicio = horaInicio;
    }
    public LocalTime getHoraFim(){
        return horaFim;
    }
    public void setHoraFim(LocalTime horaFim){
        this.horaFim = horaFim;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public Contrato getModalidade() {
        return modalidade;
    }

    public void setModalidade(Contrato modalidade) {
        this.modalidade = modalidade;
    }

}
