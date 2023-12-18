package com.totvs.desafiotovs.exception;

public class RegraNegocioErroMensagem {

    private final String erroMensagem;

    public RegraNegocioErroMensagem(String erroMensagem) {
        this.erroMensagem = erroMensagem;
    }

    public String getErrorMessage() {
        return erroMensagem;
    }
}
