package com.totvs.desafiotovs.service;

import com.totvs.desafiotovs.dto.ClienteDTO;
import com.totvs.desafiotovs.model.Cliente;

import java.util.List;

public interface ClienteService {
    List<ClienteDTO> listar();

    ClienteDTO salvar(Cliente cliente);
}
