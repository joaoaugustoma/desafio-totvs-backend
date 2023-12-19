package com.totvs.desafiotovs.service;

import com.totvs.desafiotovs.exception.RegraNegocioExceptionEnum;
import com.totvs.desafiotovs.mapper.ClienteMapper;
import com.totvs.desafiotovs.model.Cliente;
import com.totvs.desafiotovs.model.ClienteTelefone;
import com.totvs.desafiotovs.repository.ClienteRespository;
import com.totvs.desafiotovs.repository.ClienteTelefoneRepository;
import com.totvs.desafiotovs.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {

    @Mock
    private ClienteRespository clienteRepository;

    @Mock
    private ClienteTelefoneRepository clienteTelefoneRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Test
    public void testValidaTelefoneUnicoDuplicado() {
        Cliente cliente = new Cliente();
        ClienteTelefone telefone1 = new ClienteTelefone();
        telefone1.setTelefone("12345678901");
        ClienteTelefone telefone2 = new ClienteTelefone();
        telefone2.setTelefone("12345678901");
        cliente.setTelefones(Arrays.asList(telefone1, telefone2));

        List<String> erros = new ArrayList<>();
        clienteService.validaRegrasTelefone(cliente, erros);

        assertEquals(1, erros.size());
        assertTrue(erros.contains(RegraNegocioExceptionEnum.TELEFONE_DUPLICADO.formatMessage(Set.of("12345678901"))));
    }

    @Test
    public void testValidaClienteComDoisTelefonesIguais() {
        Cliente cliente = new Cliente();
        ClienteTelefone telefone1 = new ClienteTelefone();
        telefone1.setTelefone("12345678901");
        ClienteTelefone telefone2 = new ClienteTelefone();
        telefone2.setTelefone("12345678901");
        cliente.setTelefones(Arrays.asList(telefone1, telefone2));
        cliente.setNome("Teste teste teste teste");

        List<String> erros = new ArrayList<>();
        boolean resultado = clienteService.validaRegraClienteAntesSalvar(cliente, erros);

        assertFalse(resultado);
        assertEquals(1, erros.size());
        assertTrue(erros.contains(RegraNegocioExceptionEnum.TELEFONE_DUPLICADO.formatMessage(Set.of("12345678901"))));
    }

    @Test
    public void testValidaTelefoneVazio() {
        Cliente cliente = new Cliente();
        cliente.setTelefones(List.of(new ClienteTelefone()));

        List<String> erros = new ArrayList<>();
        clienteService.validaRegrasTelefone(cliente, erros);

        assertTrue(erros.contains(RegraNegocioExceptionEnum.TELEFONE_NULO_OU_VAZIO.formatMessage(1)));
    }

    @Test
    public void testValidaClienteSemNome() {
        Cliente cliente = new Cliente();

        ClienteTelefone telefone = new ClienteTelefone();
        telefone.setTelefone("12345678901");
        cliente.setTelefones(List.of(telefone));

        List<String> erros = new ArrayList<>();
        boolean resultado = clienteService.validaRegraClienteAntesSalvar(cliente, erros);

        assertFalse(resultado);
        assertEquals(1, erros.size());
        assertTrue(erros.contains(RegraNegocioExceptionEnum.NOME_CLIENTE_OBRIGATORIO.getMensagem()));
    }

    @Test
    public void testValidaRegraClienteAntesSalvarNomeMenosDeDezCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("João");

        ClienteTelefone telefone = new ClienteTelefone();
        telefone.setTelefone("12345678901");
        cliente.setTelefones(List.of(telefone));

        List<String> erros = new ArrayList<>();
        boolean resultado = clienteService.validaRegraClienteAntesSalvar(cliente, erros);

        assertFalse(resultado);
        assertEquals(1, erros.size());
        assertTrue(erros.contains(RegraNegocioExceptionEnum.NOME_CLIENTE_MENOR_QUE_10.getMensagem()));
    }
}