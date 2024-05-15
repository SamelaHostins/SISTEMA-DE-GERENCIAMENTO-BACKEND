package salao.online.application.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import salao.online.application.dtos.ClienteDTO;
import salao.online.application.mappers.AgendamentoMapper;
import salao.online.application.mappers.AvaliacaoMapper;
import salao.online.application.mappers.ClienteMapper;
import salao.online.application.services.interfaces.ClienteService;
import salao.online.domain.entities.Cliente;
import salao.online.domain.enums.MensagemErroValidacaoEnum;
import salao.online.domain.exceptions.ValidacaoException;
import salao.online.infra.repositories.ClienteRepository;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    @Inject
    ClienteMapper clienteMapper;

    @Inject
    AvaliacaoMapper avaliacaoMapper;

    @Inject
    AgendamentoMapper agendamentoMapper;

    @Inject
    ClienteRepository clienteRepository;

    private static Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    @Override
    @Transactional
    public ClienteDTO cadastrarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        logger.info("Salvando o cliente no bd");
        clienteRepository.persistAndFlush(cliente);
        return getClienteDTO(cliente);
    }

    @Override
    public ClienteDTO atualizarCadastroCliente(ClienteDTO clienteDTO) throws ValidacaoException {
        Optional<Cliente> clienteOptional = clienteRepository.findByIdOptional(clienteDTO.getIdCliente());
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            cliente.atualizarCadastroCliente(clienteDTO.getNome(), clienteDTO.getSobrenome(), clienteDTO.getIdade(),
                    clienteDTO.getEmail(), clienteDTO.getTelefone(), clienteDTO.getSenha());
            logger.info("Salvando registro atualizado");
            clienteRepository.persistAndFlush(cliente);
            return getClienteDTO(cliente);
        } else {
            throw new ValidacaoException(MensagemErroValidacaoEnum.CLIENTE_NAO_ENCONTRADO.getMensagemErro());
        }
    }

    @Override
    public ClienteDTO buscarClientePorId(UUID idCliente) throws ValidacaoException {
        logger.info("Validando se o Cliente existe");
        Cliente cliente = clienteRepository.findByIdOptional(idCliente)
                .orElseThrow(() -> new ValidacaoException(
                        MensagemErroValidacaoEnum.CLIENTE_NAO_ENCONTRADO.getMensagemErro()));
        return getClienteDTO(cliente);
    }

    @Override
    public ClienteDTO deletarCadastroCliente(UUID idCliente) throws ValidacaoException {
        logger.info("Validando se o Cliente existe");
        buscarClientePorId(idCliente);
        Optional<Cliente> cliente = clienteRepository.deletarCadastroDeCliente(idCliente);
        return getClienteDTO(cliente.get());
    }

    @Override
   public List<ClienteDTO> buscarClientesPorNome() {
      logger.info("Buscando de forma ordenada os clientes cadastrados");
      return clienteRepository.buscarClientesPorNome().stream()
            .map(cliente -> getClienteDTO(cliente))
            .collect(Collectors.toList());
   }

    private ClienteDTO getClienteDTO(Cliente cliente) {
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);
        clienteDTO.setAvaliacoes(avaliacaoMapper.toDtoList(cliente.getAvaliacoes()));
        clienteDTO.setAgendamentos(agendamentoMapper.toDtoList(cliente.getAgendamentos()));
        return clienteDTO;
    }

}
