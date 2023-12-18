package com.totvs.desafiotovs.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClienteDTO {
    private Long id;
    private String nome;
    private String endereco;
    private String bairro;
    private List<ClienteTelefoneDTO> telefones;
}
