package salao.online.application.services.impl;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import salao.online.application.dtos.dtosDeCliente.AtualizarClienteDTO;
import salao.online.application.dtos.dtosDeCliente.BuscarClienteDTO;
import salao.online.application.dtos.dtosDeCliente.CriarClienteDTO;
import salao.online.application.mappers.AgendamentoMapper;
import salao.online.application.mappers.AvaliacaoMapper;
import salao.online.application.mappers.ClienteMapper;
import salao.online.application.mappers.ImagemMapper;
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
    ImagemMapper imagemMapper;

    @Inject
    ClienteRepository clienteRepository;

    private static Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    private String removeAcentos(String texto) {
        if (texto == null) {
            return null;
        }
        String normalized = Normalizer.normalize(texto, Normalizer.Form.NFD);
        return normalized.replaceAll("[^\\p{ASCII}]", "");
    }

    @Override
    @Transactional
    public CriarClienteDTO cadastrarCliente(CriarClienteDTO clienteDTO) {
        try {
            Cliente cliente = clienteMapper.fromCriarDtoToEntity(clienteDTO);

            String[] sobrenomes = clienteDTO.getSobrenome().split(" ");
            String ultimoSobrenome = sobrenomes[sobrenomes.length - 1];
            String usuario = removeAcentos(clienteDTO.getNome().toLowerCase()) + "."
                    + removeAcentos(ultimoSobrenome.toLowerCase());
            cliente.setUsuario(usuario);

            logger.info("Salvando o cliente no banco de dados");
            clienteRepository.persistAndFlush(cliente);

            return clienteMapper.fromEntityToCriarDto(cliente);
        } catch (Exception e) {
            logger.error("Erro ao cadastrar cliente", e);
            throw new RuntimeException("Erro ao cadastrar cliente.", e);
        }
    }

    @Override
    public BuscarClienteDTO buscarClientePorId(UUID idCliente) throws ValidacaoException {
        logger.info("Validando se o Cliente existe");
        Cliente cliente = clienteRepository.findByIdOptional(idCliente)
                .orElseThrow(() -> new ValidacaoException(
                        MensagemErroValidacaoEnum.CLIENTE_NAO_ENCONTRADO.getMensagemErro()));
        return getBuscarClienteDTO(cliente);
    }

    @Override
    public List<BuscarClienteDTO> buscarClientesPorNome() {
        logger.info("Buscando de forma ordenada os clientes cadastrados");
        return clienteRepository.buscarClientesPorNome().stream()
                .map(cliente -> getBuscarClienteDTO(cliente))
                .collect(Collectors.toList());
    }

    @Override
    public void deletarCliente(UUID idCliente) throws ValidacaoException {
        logger.info("Validando se o Cliente existe");
        buscarClientePorId(idCliente);
        clienteRepository.deletarCadastroDeCliente(idCliente);
    }

    @Override
    public AtualizarClienteDTO atualizarCliente(AtualizarClienteDTO clienteDTO) throws ValidacaoException {
        Optional<Cliente> clienteOptional = clienteRepository.findByIdOptional(clienteDTO.getIdCliente());
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            cliente.atualizarCliente(clienteDTO.getNome(), clienteDTO.getSobrenome(), clienteDTO.getIdade(),
                    clienteDTO.getEmail(),
                    clienteDTO.getTelefone(), clienteDTO.getSenha());
            logger.info("Salvando registro atualizado");
            clienteRepository.persistAndFlush(cliente);
            return clienteMapper.fromEntityToAtualizarDto(cliente);
        } else {
            throw new ValidacaoException(MensagemErroValidacaoEnum.CLIENTE_NAO_ENCONTRADO.getMensagemErro());
        }
    }

    @Override
    @Transactional
    public boolean atualizarClienteEspecial(UUID idCliente) throws ValidacaoException {
        logger.info("Atualizando cliente para especial");
        Cliente cliente = clienteRepository.findById(idCliente);
        if (cliente != null) {
            cliente.atualizarClienteEspecial();
            clienteRepository.persist(cliente);
            return true;
        }
        return false;
    }

    @Override
    public Map<String, Integer> obterFaixasEtariasDasClientes() {
        List<BuscarClienteDTO> clientes = clienteRepository.buscarClientesPorNome().stream()
                .map(cliente -> getBuscarClienteDTO(cliente))
                .collect(Collectors.toList());
        Map<String, Integer> distribuicao = new HashMap<>();
        distribuicao.put("abaixo_18", 0);
        distribuicao.put("de_18_a_25", 0);
        distribuicao.put("de_25_a_30", 0);
        distribuicao.put("de_30_a_40", 0);
        distribuicao.put("acima_40", 0);

        for (BuscarClienteDTO cliente : clientes) {
            short idade = cliente.getIdade();
            if (idade < 18) {
                distribuicao.put("abaixo_18", distribuicao.get("abaixo_18") + 1);
            } else if (idade <= 25) {
                distribuicao.put("de_18_a_25", distribuicao.get("de_18_a_25") + 1);
            } else if (idade <= 30) {
                distribuicao.put("de_25_a_30", distribuicao.get("de_25_a_30") + 1);
            } else if (idade <= 40) {
                distribuicao.put("de_30_a_40", distribuicao.get("de_30_a_40") + 1);
            } else {
                distribuicao.put("acima_40", distribuicao.get("acima_40") + 1);
            }
        }

        return distribuicao;
    }

    private BuscarClienteDTO getBuscarClienteDTO(Cliente cliente) {
        BuscarClienteDTO clienteDTO = clienteMapper.fromEntityToBuscarDto(cliente);
        clienteDTO.setAvaliacoes(avaliacaoMapper.fromEntityListToDtoList(cliente.getAvaliacoes()));
        clienteDTO.setAgendamentos(agendamentoMapper.fromEntityListToDtoList(cliente.getAgendamentos()));
        clienteDTO.setImagens(imagemMapper.fromEntityListToClienteDtoList(cliente.getImagens()));
        return clienteDTO;
    }

}
