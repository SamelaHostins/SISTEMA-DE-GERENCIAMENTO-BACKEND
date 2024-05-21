package salao.online.application.services.interfaces;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import salao.online.application.dtos.dtosDeCliente.AtualizarClienteDTO;
import salao.online.application.dtos.dtosDeCliente.BuscarClienteDTO;
import salao.online.application.dtos.dtosDeCliente.ClienteDTO;
import salao.online.application.dtos.dtosDeCliente.CriarClienteDTO;
import salao.online.domain.exceptions.ValidacaoException;

public interface ClienteService {

    public CriarClienteDTO cadastrarCliente(CriarClienteDTO clienteDTO);

    public AtualizarClienteDTO atualizarCadastroCliente(AtualizarClienteDTO clienteDTO) throws ValidacaoException;

    public boolean atualizarClienteEspecial(UUID idCliente) throws ValidacaoException;

    public BuscarClienteDTO buscarClientePorId(UUID idCliente) throws ValidacaoException;

    public ClienteDTO deletarCadastroCliente(UUID idCliente) throws ValidacaoException;

    public List<BuscarClienteDTO> buscarClientesPorNome();

    public Map<String, Integer> obterFaixasEtariasDasClientes();
}
