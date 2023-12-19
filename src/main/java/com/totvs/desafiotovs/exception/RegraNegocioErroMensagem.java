package com.totvs.desafiotovs.exception;

public record RegraNegocioErroMensagem(String erroMensagem) {

    public String getErroMensagem() {
        return erroMensagem;
    }
}
