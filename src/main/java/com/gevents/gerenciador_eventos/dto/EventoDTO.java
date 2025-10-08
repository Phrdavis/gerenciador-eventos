package com.gevents.gerenciador_eventos.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.gevents.gerenciador_eventos.model.Contrato;
import com.gevents.gerenciador_eventos.model.Modalidade;
import com.gevents.gerenciador_eventos.model.Status;

public class EventoDTO {

    private String nome; 
    private String modelo;
    private String upload;
    private String numSolicitacao;
    private String destino;
    private String descricao;
    private String pdf;
    private LocalDate dataSolicitacao;
    private List<LocalDate> datas;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private String local;
    private String responsavel;
    private String telefoneResponsavel;
    private Status status;
    private Contrato contrato;
    private Modalidade modalidade;
    private int qtdModalidade;

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
    public LocalDate  getDataSolicitacao(){
        return dataSolicitacao;
    }
    public void setDataSolicitacao(LocalDate  dataSolicitacao){
        this.dataSolicitacao = dataSolicitacao;
    }
    public List<LocalDate> getDatas(){
        return datas;
    }
    public void setDatas(List<LocalDate> datas){
        this.datas = datas;
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

    public Modalidade getModalidade() {
        return modalidade;
    }

    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }

    public int getQtdModalidade() {
        return qtdModalidade;
    }
    public void setQtdModalidade(int qtdModalidade) {
        this.qtdModalidade = qtdModalidade;
    }
}
