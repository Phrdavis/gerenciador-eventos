package com.gevents.gerenciador_eventos.util;

import com.gevents.gerenciador_eventos.enums.StatusErro;

public class ErroExtracao {

    private String codigo;
    private String mensagem;
    private String arquivo;
    private StatusErro status;

    public ErroExtracao(String codigo, String mensagem, String arquivo, StatusErro status) {
        this.codigo = codigo;
        this.mensagem = mensagem;
        this.arquivo = arquivo;
        this.status = status;
    }

    // Getters
    public String getCodigo() {
        return codigo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getArquivo() {
        return arquivo;
    }

    public StatusErro getStatus() {
        return status;
    }
    
}
