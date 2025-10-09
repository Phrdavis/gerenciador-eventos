package com.gevents.gerenciador_eventos.enums;

public enum StatusErro {
    ERROR, // Erro fatal na extração, evento não pode ser criado.
    WARN   // Aviso, dado não encontrado mas o evento pode ser criado.
}
