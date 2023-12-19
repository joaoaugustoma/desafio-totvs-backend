package com.totvs.desafiotovs.exception;

import lombok.Getter;

import java.util.Arrays;
import java.util.Set;

@Getter
public enum RegraNegocioExceptionEnum {

    NOME_CLIENTE_OBRIGATORIO("Nome do cliente é obrigatório."),
    NOME_CLIENTE_MENOR_QUE_10("Nome do cliente deve ter mais de 10 caracteres."),
    NOME_CLIENTE_EXISTENTE("Nome do cliente já cadastrado."),
    CLIENTE_SEM_TELEFONE("O cliente deve ter pelo menos um telefone."),
    TELEFONE_NULO_OU_VAZIO("O %dº telefone está nulo ou vazio."),
    TELEFONE_DUPLICADO("Foram encontrados números de telefone duplicados: %s."),
    TELEFONE_FORMATO_INCORRETO("O %dº telefone %s não está no formato correto."),
    TELEFONE_EXISTENTE("O %dº telefone %s já está sendo utilizado.");

    private final String mensagem;

    RegraNegocioExceptionEnum(String mensagem) {
        this.mensagem = mensagem;
    }

    public String formatMessage(int index, Object... args) {
        return String.format(mensagem, index, Arrays.toString(args));
    }

    public String formatMessage(Set<String> mensagens) {
        return String.format(mensagem, mensagens);
    }
}
