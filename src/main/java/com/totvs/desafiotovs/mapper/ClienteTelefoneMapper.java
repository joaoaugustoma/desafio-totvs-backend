package com.totvs.desafiotovs.mapper;

import com.totvs.desafiotovs.dto.ClienteTelefoneDTO;
import com.totvs.desafiotovs.model.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {Cliente.class})
public interface ClienteTelefoneMapper {
    ClienteTelefoneDTO toDTO(Cliente cliente);

    Cliente toEntity(ClienteTelefoneDTO clienteTelefoneDTO);
}
