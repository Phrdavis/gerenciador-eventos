package com.gevents.gerenciador_eventos.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String modelo;
    private String upload;
    private String destino;
    private String descricao;
    private String numSolicitacao;
    private String pdf;
    private LocalDate data;
    private LocalDate inicio;
    private LocalDate fim;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private String local;
    private String responsavel;
    private String telefoneResponsavel;
    
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
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

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return "Nome: " + nome + "\n" +
               "Modelo: " + modelo + "\n" +
               "Upload: " + upload + "\n" +
               "Destino: " + destino + "\n" +
               "Descricao: " + descricao + "\n" +
               "Pdf: " + pdf + "\n" +
               "Número de Solicitação: " + numSolicitacao + "\n" +
               "Data: " + (data != null ? data.format(dateFormatter) : "Não especificada") + "\n" +
               "Início: " + (inicio != null ? inicio.format(dateFormatter) : "Não especificado") + "\n" +
               "Fim: " + (fim != null ? fim.format(dateFormatter) : "Não especificado") + "\n" +
               "Hora de Início: " + (horaInicio != null ? horaInicio.format(timeFormatter) : "Não especificada") + "\n" +
               "Hora de Fim: " + (horaFim != null ? horaFim.format(timeFormatter) : "Não especificada") + "\n" +
               "Local: " + local + "\n" +
               "Responsável: " + responsavel + "\n" +
               "Telefone do Responsável: " + telefoneResponsavel;
    }
    
}
