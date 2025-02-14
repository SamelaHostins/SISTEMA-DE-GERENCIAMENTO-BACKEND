package salao.online.application.services.interfaces;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import salao.online.application.dtos.dtosDeCliente.AtualizarClienteDTO;
import salao.online.application.dtos.dtosDeCliente.BuscarClienteDTO;
import salao.online.application.dtos.dtosDeCliente.CriarClienteDTO;
import salao.online.domain.exceptions.ValidacaoException;

public interface ClienteService {

    public CriarClienteDTO cadastrarCliente(CriarClienteDTO clienteDTO) throws ValidacaoException;

    public AtualizarClienteDTO atualizarCliente(AtualizarClienteDTO clienteDTO) throws ValidacaoException;

    public BuscarClienteDTO buscarClientePorId(UUID idCliente) throws ValidacaoException;

    public void deletarCliente(UUID idCliente) throws ValidacaoException;

    public List<BuscarClienteDTO> buscarClientes();

    public Map<String, Integer> obterFaixasEtariasDasClientes();
}
