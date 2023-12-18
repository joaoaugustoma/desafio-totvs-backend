package com.totvs.desafiotovs.repository;

import com.totvs.desafiotovs.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRespository extends JpaRepository<Cliente, Long> {
    Cliente findClienteByNome(String nome);
}
