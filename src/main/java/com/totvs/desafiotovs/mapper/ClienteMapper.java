package com.totvs.desafiotovs.mapper;

import com.totvs.desafiotovs.dto.ClienteDTO;
import com.totvs.desafiotovs.model.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    ClienteDTO toDTO(Cliente cliente);

    Cliente toEntity(ClienteDTO clienteDTO);
}
