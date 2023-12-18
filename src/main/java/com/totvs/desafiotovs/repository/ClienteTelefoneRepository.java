package com.totvs.desafiotovs.repository;

import com.totvs.desafiotovs.model.ClienteTelefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteTelefoneRepository extends JpaRepository<ClienteTelefone, Long> {
    List<ClienteTelefone> findAllByClienteId(Long id);

    ClienteTelefone findClienteTelefoneByTelefone(String telefone);
}
