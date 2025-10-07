package com.gevents.gerenciador_eventos.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.gevents.gerenciador_eventos.model.Contrato;
import com.gevents.gerenciador_eventos.model.Modalidade;
import com.gevents.gerenciador_eventos.model.Status;

public class EventoFilterDTO {

    private LocalDate inicio;
    private LocalDate fim;
    private int anoAtual;
    private int mesAtual;
    private int view;

    public int getAnoAtual() {
        return anoAtual;
    }
    public void setAnoAtual(int anoAtual) {
        this.anoAtual = anoAtual;
    }
    public int getMesAtual() {
        return mesAtual;
    }
    public void setMesAtual(int mesAtual) {
        this.mesAtual = mesAtual;
    }
    public int getView() {
        return view;
    }
    public void setView(int view) {
        this.view = view;
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
    
}
