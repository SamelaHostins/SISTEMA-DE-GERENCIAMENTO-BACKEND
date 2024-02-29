package salao.online.application.services.interfaces;

import java.util.UUID;

import salao.online.application.dtos.ClienteDTO;
import salao.online.domain.exceptions.ValidacaoException;

public interface ClienteService {

    public ClienteDTO inserirCliente(ClienteDTO clienteDTO);

    public ClienteDTO atualizarCliente(ClienteDTO clienteDTO) throws ValidacaoException;

    public ClienteDTO buscarClientePorId(UUID idCliente) throws ValidacaoException;

}
