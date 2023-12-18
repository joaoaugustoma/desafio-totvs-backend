package com.totvs.desafiotovs.controller;

import com.totvs.desafiotovs.dto.ClienteDTO;
import com.totvs.desafiotovs.model.Cliente;
import com.totvs.desafiotovs.service.impl.ClienteServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ClienteDTO> salvar(@RequestBody Cliente cliente) {
        try {
            ClienteDTO clienteCadastrado = clienteService.salvar(cliente);
            return ResponseEntity.ok(clienteCadastrado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping()
    public ResponseEntity<ClienteDTO> editar(@RequestBody Cliente cliente) {
        if(cliente.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            ClienteDTO clienteCadastrado = clienteService.editar(cliente);
            return ResponseEntity.ok(clienteCadastrado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
