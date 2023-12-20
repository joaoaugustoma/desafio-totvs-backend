package com.totvs.desafiotovs.service.impl;

import com.totvs.desafiotovs.dto.ClienteDTO;
import com.totvs.desafiotovs.exception.RegraNegocioException;
import com.totvs.desafiotovs.exception.RegraNegocioExceptionEnum;
import com.totvs.desafiotovs.mapper.ClienteMapper;
import com.totvs.desafiotovs.model.Cliente;
import com.totvs.desafiotovs.model.ClienteTelefone;
import com.totvs.desafiotovs.repository.ClienteRespository;
import com.totvs.desafiotovs.repository.ClienteTelefoneRepository;
import com.totvs.desafiotovs.service.ClienteService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRespository clienteRepository;

    private final ClienteTelefoneRepository clienteTelefoneRepository;

    private final ClienteMapper clienteMapper;

    public ClienteServiceImpl(ClienteRespository clienteRepository, ClienteTelefoneRepository clienteTelefoneRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteTelefoneRepository = clienteTelefoneRepository;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public List<ClienteDTO> listar() {
        return clienteRepository.findAll().stream().map(clienteMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ClienteDTO salvar(Cliente cliente) {
        List<String> erros = new ArrayList<>();

        if (validaRegraClienteAntesSalvar(cliente, erros)) {
            salvarClienteETelefones(cliente);
            return clienteMapper.toDTO(cliente);
        } else {
            throw new RegraNegocioException("Cliente não pode ser cadastrado.", erros);
        }
    }

    private void salvarClienteETelefones(Cliente cliente) {
        clienteRepository.save(cliente);

        for (ClienteTelefone telefone : cliente.getTelefones()) {
            telefone.setCliente(cliente);
            clienteTelefoneRepository.save(telefone);
        }
    }

    public boolean validaRegraClienteAntesSalvar(Cliente cliente, List<String> erros) {
        validaNomeCliente(cliente, erros);
        validaRegrasTelefone(cliente, erros);

        return erros.isEmpty();
    }

    private void validaNomeCliente(Cliente cliente, List<String> erros) {
        if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
            erros.add(RegraNegocioExceptionEnum.NOME_CLIENTE_OBRIGATORIO.getMensagem());
        } else {
            if (cliente.getNome().length() <= 10) {
                erros.add(RegraNegocioExceptionEnum.NOME_CLIENTE_MENOR_QUE_10.getMensagem());
            } else if (clienteRepository.findClienteByNome(cliente.getNome()) != null) {
                erros.add("Nome do cliente já cadastrado.");
            }
        }
    }

    public void validaRegrasTelefone(Cliente cliente, List<String> erros) {
        if (cliente.getTelefones() == null || cliente.getTelefones().isEmpty()) {
            erros.add(RegraNegocioExceptionEnum.CLIENTE_SEM_TELEFONE.getMensagem());
        } else {
            if (validaRegraTelefoneUnico(cliente, erros)) {
                int index = 0;

                for (ClienteTelefone clienteTelefone : cliente.getTelefones()) {
                    index++;
                    if (Strings.isBlank(clienteTelefone.getTelefone())) {
                        erros.add(RegraNegocioExceptionEnum.TELEFONE_NULO_OU_VAZIO.formatMessage(index));
                    } else {
                        if (!validaFormatoTelefone(clienteTelefone.getTelefone())) {
                            erros.add(RegraNegocioExceptionEnum.TELEFONE_FORMATO_INCORRETO.formatMessage(index, clienteTelefone.getTelefone()));
                        } else {
                            ClienteTelefone clienteTelefoneByTelefone = clienteTelefoneRepository.findClienteTelefoneByTelefone(clienteTelefone.getTelefone());

                            if (clienteTelefoneByTelefone != null) {
                                if (clienteTelefone.getId() == null || !clienteTelefoneByTelefone.getCliente().getId().equals(clienteTelefone.getId())) {
                                    erros.add(RegraNegocioExceptionEnum.TELEFONE_EXISTENTE.formatMessage(index, clienteTelefone.getTelefone()));
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    private boolean validaFormatoTelefone(String telefone) {
        String regex = "\\d{11}";
        return telefone.matches(regex);
    }

    public boolean validaRegraTelefoneUnico(Cliente cliente, List<String> erros) {
        Set<String> telefones = new HashSet<>();

        boolean isTelefoneRepetido = cliente.getTelefones().stream()
                .map(ClienteTelefone::getTelefone)
                .anyMatch(phone -> !telefones.add(phone));

        if (isTelefoneRepetido) {
            erros.add(RegraNegocioExceptionEnum.TELEFONE_DUPLICADO.formatMessage(telefones));
        }

        return !isTelefoneRepetido;
    }
}
