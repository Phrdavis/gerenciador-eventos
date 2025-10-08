package com.gevents.gerenciador_eventos.dto;

import java.time.LocalDate;
import java.util.List;

public class EventoFilterDTO {

    private List<LocalDate> datas;
    private int anoAtual;
    private int mesAtual;
    private String view;

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
    public String getView() {
        return view;
    }
    public void setView(String view) {
        this.view = view;
    }
 
    public List<LocalDate>  getDatas(){
        return datas;
    }
    public void setDatas(List<LocalDate>  datas){
        this.datas = datas;
    }
    
}
