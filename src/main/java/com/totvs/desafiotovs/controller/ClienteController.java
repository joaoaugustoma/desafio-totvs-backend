package com.totvs.desafiotovs.controller;

import com.totvs.desafiotovs.dto.ClienteDTO;
import com.totvs.desafiotovs.exception.RegraNegocioException;
import com.totvs.desafiotovs.model.Cliente;
import com.totvs.desafiotovs.service.impl.ClienteServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/cliente")
@CrossOrigin(origins = "*")
public class ClienteController {

    private final ClienteServiceImpl clienteService;

    public ClienteController(ClienteServiceImpl clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listar() {
        List<ClienteDTO> clientes = clienteService.listar();
        return ResponseEntity.ok(clientes);
    }

    @PostMapping()
    public ResponseEntity<?> salvar(@RequestBody Cliente cliente) {
        try {
            ClienteDTO clienteCadastrado = clienteService.salvar(cliente);
            return ResponseEntity.ok(clienteCadastrado);
        } catch (RegraNegocioException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getErrosMensagem());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestBody Cliente cliente) {
        try {
            validarClienteParaEdicao(cliente);

            ClienteDTO clienteCadastrado = clienteService.editar(cliente);
            return ResponseEntity.ok(clienteCadastrado);
        } catch (RegraNegocioException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getErrosMensagem());
        }
    }

    private void validarClienteParaEdicao(Cliente cliente) {
        if (cliente.getId() == null) {
            throw new RegraNegocioException("Id do cliente é obrigatório.", Collections.singletonList("Id do cliente é obrigatório."));
        }
    }

}
