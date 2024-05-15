package salao.online.application.services.interfaces;

import java.util.List;
import java.util.UUID;

import salao.online.application.dtos.ClienteDTO;
import salao.online.domain.exceptions.ValidacaoException;

public interface ClienteService {

    public ClienteDTO cadastrarCliente(ClienteDTO clienteDTO);

    public ClienteDTO atualizarCadastroCliente(ClienteDTO clienteDTO) throws ValidacaoException;

    public ClienteDTO buscarClientePorId(UUID idCliente) throws ValidacaoException;

    public ClienteDTO deletarCadastroCliente(UUID idCliente) throws ValidacaoException;

    public List<ClienteDTO> buscarClientesPorNome();
}
