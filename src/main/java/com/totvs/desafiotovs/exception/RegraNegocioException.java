package com.totvs.desafiotovs.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegraNegocioException extends RuntimeException {
    private final List<RegraNegocioErroMensagem> erros = new ArrayList<>();

    public RegraNegocioException(String mensagemErro, List<String> erros) {
        super(mensagemErro);
        erros.forEach(erro -> this.erros.add(new RegraNegocioErroMensagem(erro)));
    }

    public List<RegraNegocioErroMensagem> getErrosMensagem() {
        return erros;
    }
}
