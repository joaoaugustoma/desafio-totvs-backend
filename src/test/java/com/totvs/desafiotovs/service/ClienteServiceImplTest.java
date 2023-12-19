package com.totvs.desafiotovs.service;

import com.totvs.desafiotovs.dto.ClienteDTO;
import com.totvs.desafiotovs.exception.RegraNegocioErroMensagem;
import com.totvs.desafiotovs.exception.RegraNegocioException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {

    @Mock
    private ClienteRespository clienteRepository;

    @Mock
    private ClienteTelefoneRepository clienteTelefoneRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Test
    public void testValidaTelefoneVazio() {
        Cliente cliente = new Cliente();
        cliente.setTelefones(Arrays.asList(new ClienteTelefone(), new ClienteTelefone()));

        List<String> erros = new ArrayList<>();
        clienteService.validaRegrasTelefone(cliente, erros);

        assertEquals(2, erros.size());
        assertTrue(erros.contains(RegraNegocioExceptionEnum.TELEFONE_NULO_OU_VAZIO.formatMessage(1)));
        assertTrue(erros.contains(RegraNegocioExceptionEnum.TELEFONE_NULO_OU_VAZIO.formatMessage(2)));
    }

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
        assertTrue(erros.contains(RegraNegocioExceptionEnum.TELEFONE_EXISTENTE.formatMessage(1, "12345678901")));
    }

    @Test
    public void testValidaRegraClienteAntesSalvarNomeMenosDeDezCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("João");

        List<String> erros = new ArrayList<>();
        boolean resultado = clienteService.validaRegraClienteAntesSalvar(cliente, erros);

        assertFalse(resultado);
        assertEquals(1, erros.size());
        assertTrue(erros.contains(RegraNegocioExceptionEnum.NOME_CLIENTE_MENOR_QUE_10.getMensagem()));
    }

    @Test
    public void testValidaClienteComDoisTelefonesIguais() {
        Cliente cliente = new Cliente();
        ClienteTelefone telefone1 = new ClienteTelefone();
        telefone1.setTelefone("12345678901");
        ClienteTelefone telefone2 = new ClienteTelefone();
        telefone2.setTelefone("12345678901");
        cliente.setTelefones(Arrays.asList(telefone1, telefone2));

        List<String> erros = new ArrayList<>();
        boolean resultado = clienteService.validaRegraClienteAntesSalvar(cliente, erros);

        assertFalse(resultado);
        assertEquals(1, erros.size());
        assertTrue(erros.contains(RegraNegocioExceptionEnum.TELEFONE_EXISTENTE.formatMessage(1, "12345678901")));
    }

    @Test
    public void testValidaClienteSemNome() {
        Cliente cliente = new Cliente();

        List<String> erros = new ArrayList<>();
        boolean resultado = clienteService.validaRegraClienteAntesSalvar(cliente, erros);

        assertFalse(resultado);
        assertEquals(1, erros.size());
        assertTrue(erros.contains(RegraNegocioExceptionEnum.NOME_CLIENTE_OBRIGATORIO.getMensagem()));
    }

    @Test
    public void testCriarClienteInvalido() {
        Cliente cliente = new Cliente(null, "João", "Rua L-21", "Jd Europa", new ArrayList<>());
        RegraNegocioException exception = assertThrows(RegraNegocioException.class, () -> clienteService.salvar(cliente));

        List<String> erros = exception.getErrosMensagem().stream().map(RegraNegocioErroMensagem::getErroMensagem).toList();

        assertTrue(erros.contains(RegraNegocioExceptionEnum.NOME_CLIENTE_OBRIGATORIO.getMensagem()));
        assertTrue(erros.contains(RegraNegocioExceptionEnum.NOME_CLIENTE_MENOR_QUE_10.getMensagem());
        assertTrue(erros.contains(RegraNegocioExceptionEnum.CLIENTE_SEM_TELEFONE.getMensagem()));
    }

    @Test
    public void testEditarCliente() {
        Cliente clienteParaEditar = new Cliente();
        clienteParaEditar.setId(1L);
        clienteParaEditar.setNome("Novo Nome");
        ClienteTelefone telefone1 = new ClienteTelefone();
        telefone1.setId(1L);
        telefone1.setTelefone("12345678901");
        ClienteTelefone telefone2 = new ClienteTelefone();
        telefone2.setId(2L);
        telefone2.setTelefone("98765432109");
        clienteParaEditar.setTelefones(Arrays.asList(telefone1, telefone2));

        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteParaEditar);

        when(clienteMapper.toDTO(any(Cliente.class))).thenReturn(new ClienteDTO());

        ClienteDTO clienteEditadoDTO = clienteService.editar(clienteParaEditar);

        assertNotNull(clienteEditadoDTO);
        assertEquals("Novo Nome", clienteEditadoDTO.getNome());
    }
}