package salao.online.application.services.interfaces;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import salao.online.application.dtos.dtosDeCliente.AtualizarClienteDTO;
import salao.online.application.dtos.dtosDeCliente.BuscarClienteDTO;
import salao.online.application.dtos.dtosDeCliente.CriarClienteDTO;
import salao.online.domain.exceptions.ValidacaoException;

public interface ClienteService {

    CriarClienteDTO cadastrarCliente(CriarClienteDTO clienteDTO) throws ValidacaoException;

    AtualizarClienteDTO atualizarCliente(AtualizarClienteDTO clienteDTO) throws ValidacaoException;

    BuscarClienteDTO buscarClientePorId(UUID idCliente) throws ValidacaoException;

    void deletarCliente(UUID idCliente) throws ValidacaoException;

    List<BuscarClienteDTO> buscarClientes();

    Map<String, Integer> obterFaixasEtariasDasClientes();

    void alterarSenha(UUID idCliente, String novaSenha) throws ValidacaoException;

}
