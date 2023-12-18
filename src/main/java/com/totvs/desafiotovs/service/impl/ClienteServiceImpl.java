package com.totvs.desafiotovs.service.impl;

import com.totvs.desafiotovs.dto.ClienteDTO;
import com.totvs.desafiotovs.exception.RegraNegocioException;
import com.totvs.desafiotovs.mapper.ClienteMapper;
import com.totvs.desafiotovs.mapper.ClienteTelefoneMapper;
import com.totvs.desafiotovs.model.Cliente;
import com.totvs.desafiotovs.model.ClienteTelefone;
import com.totvs.desafiotovs.repository.ClienteRespository;
import com.totvs.desafiotovs.repository.ClienteTelefoneRepository;
import com.totvs.desafiotovs.service.ClienteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRespository clienteRepository;

    private final ClienteTelefoneRepository clienteTelefoneRepository;

    private final ClienteTelefoneMapper clienteTelefoneMapper;

    private final ClienteMapper clienteMapper;

    public ClienteServiceImpl(ClienteRespository clienteRepository, ClienteTelefoneRepository clienteTelefoneRepository, ClienteTelefoneMapper clienteTelefoneMapper, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteTelefoneRepository = clienteTelefoneRepository;
        this.clienteTelefoneMapper = clienteTelefoneMapper;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public List<ClienteDTO> listar() {
        return clienteRepository.findAll().stream().map(clienteMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ClienteDTO salvar(Cliente cliente) {
        List<String> erros = new ArrayList<>();
        if(validaRegraClienteAntesSalvar(cliente, erros)){
            clienteRepository.save(cliente);

            cliente.getTelefones().forEach(telefone -> {
                telefone.setCliente(cliente);
                clienteTelefoneRepository.save(telefone);
            });

            return clienteMapper.toDTO(cliente);
        } else {
            throw new RegraNegocioException("Cliente não pode ser cadastrado.", erros);
        }
    }

    @Override
    public ClienteDTO editar(Cliente cliente) {
        List<String> erros = new ArrayList<>();

        if(cliente.getId() == null) {
            throw new RegraNegocioException("Id do cliente é obrigatório.", erros);
        }

        if(validaRegraClienteAntesSalvar(cliente, erros)){
            List<ClienteTelefone> clienteTelefones = clienteTelefoneRepository.findAllByClienteId(cliente.getId());

            clienteTelefones.forEach(clienteTelefonesAntigo -> {
                AtomicBoolean existeTelefone = new AtomicBoolean(false);

                cliente.getTelefones().forEach(telefoneNovo -> {
                    if(clienteTelefonesAntigo.getTelefone().equals(telefoneNovo.getTelefone())){
                        telefoneNovo.setId(clienteTelefonesAntigo.getId());
                        existeTelefone.set(true);
                    }

                    if(!existeTelefone.get()){
                        clienteTelefoneRepository.delete(clienteTelefonesAntigo);
                    }
                });
            });

            clienteRepository.save(cliente);

            cliente.getTelefones().forEach(telefone -> {
                telefone.setCliente(cliente);
                clienteTelefoneRepository.save(telefone);
            });

            return clienteMapper.toDTO(cliente);
        } else {
            throw new RegraNegocioException("Cliente não pode ser editado.", erros);
        }
    }

    //TODO validar clientes antes de salver
    private boolean validaRegraClienteAntesSalvar(Cliente cliente, List<String> erros) {
        if(cliente.getNome() == null || cliente.getNome().isEmpty()){
            erros.add("Nome do cliente é obrigatório.");
        } else {
            if(cliente.getNome().length() <= 10){
                erros.add("Nome do cliente deve ter mais de 10 caracteres.");
            } else if (cliente.getId() == null && clienteRepository.findClienteByNome(cliente.getNome()) != null) {
                erros.add("Nome do cliente já cadastrado.");
            }
        }
        validaRegraTelefoneAntesSalvar(cliente, erros);
        return erros.isEmpty();
    }

    //TODO validar telefones antes de salver
    private void validaRegraTelefoneAntesSalvar(Cliente cliente, List<String> erros) {
    }

    //TODO validar telefones unicos
    private void validaRegraTelefoneUnico(Cliente cliente, List<String> erros) {
    }
}
