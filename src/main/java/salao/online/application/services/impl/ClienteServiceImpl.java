package salao.online.application.services.impl;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mindrot.jbcrypt.BCrypt;

import io.quarkus.logging.Log;
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
    public CriarClienteDTO cadastrarCliente(CriarClienteDTO clienteDTO) throws ValidacaoException {
        try {
            if (clienteRepository.find("email", clienteDTO.getEmail()).firstResultOptional().isPresent()) {
                throw new ValidacaoException(
                        MensagemErroValidacaoEnum.EMAIL_JA_CADASTRADO.getMensagemErro() + " " + clienteDTO.getEmail());
            }

            if (clienteRepository.find("documento", clienteDTO.getDocumento()).firstResultOptional().isPresent()) {
                throw new ValidacaoException(MensagemErroValidacaoEnum.DOCUMENTO_JA_CADASTRADO.getMensagemErro() + " "
                        + clienteDTO.getDocumento());
            }

            Cliente cliente = clienteMapper.fromCriarDtoToEntity(clienteDTO);

            String[] sobrenomes = clienteDTO.getSobrenome().split(" ");
            String ultimoSobrenome = sobrenomes[sobrenomes.length - 1];
            String usuario = removeAcentos(clienteDTO.getNome().toLowerCase()) + "."
                    + removeAcentos(ultimoSobrenome.toLowerCase());
            cliente.setUsuario(usuario);

            // Criptografa a senha antes de salvar
            String senhaCriptografada = BCrypt.hashpw(clienteDTO.getSenha(), BCrypt.gensalt());
            cliente.setSenha(senhaCriptografada);

            logger.info("Salvando o cliente no banco de dados");
            clienteRepository.persistAndFlush(cliente);

            return clienteMapper.fromEntityToCriarDto(cliente);

        } catch (ValidacaoException e) {
            logger.warn("Erro de validação ao cadastrar cliente: " + e.getMessage());
            throw e; // Lança a exceção personalizada para ser tratada no front-end
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
    public List<BuscarClienteDTO> buscarClientes() {
        logger.info("Buscando de forma ordenada os clientes cadastrados");
        return clienteRepository.buscarClientes().stream()
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
        Cliente cliente = clienteRepository
                .findByIdOptional(clienteDTO.getIdCliente())
                .orElseThrow(() -> new ValidacaoException(
                        MensagemErroValidacaoEnum.CLIENTE_NAO_ENCONTRADO.getMensagemErro()));

        cliente.atualizarCliente(
                clienteDTO.getNome(),
                clienteDTO.getUsuario(),
                clienteDTO.getSobrenome(),
                clienteDTO.getEmail(),
                clienteDTO.getTelefone());

        Log.infof("Salvando cliente %s atualizado", cliente.getIdCliente());
        clienteRepository.persistAndFlush(cliente);
        return clienteMapper.fromEntityToAtualizarDto(cliente);
    }

    @Override
    public Map<String, Integer> obterFaixasEtariasDasClientes() {
        List<BuscarClienteDTO> clientes = clienteRepository.buscarClientes().stream()
                .map(cliente -> getBuscarClienteDTO(cliente))
                .collect(Collectors.toList());

        Map<String, Integer> distribuicao = new HashMap<>();
        distribuicao.put("abaixo_18", 0);
        distribuicao.put("de_18_a_25", 0);
        distribuicao.put("de_25_a_30", 0);
        distribuicao.put("de_30_a_40", 0);
        distribuicao.put("acima_40", 0);

        for (BuscarClienteDTO cliente : clientes) {
            int idade = calcularIdade(cliente.getDataNascimento());

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

    @Override
    @Transactional
    public void alterarSenha(UUID idCliente, String novaSenha) throws ValidacaoException {
        Cliente cliente = clienteRepository.findById(idCliente);
        if (cliente == null) {
            throw new ValidacaoException(
                    MensagemErroValidacaoEnum.CLIENTE_NAO_ENCONTRADO.getMensagemErro());
        }

        String senhaCriptografada = BCrypt.hashpw(novaSenha, BCrypt.gensalt());
        cliente.setSenha(senhaCriptografada);

        cliente.setSenha(novaSenha);
        clienteRepository.persistAndFlush(cliente);
    }

    // Calcula a idade com base na data de nascimento
    private int calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    private BuscarClienteDTO getBuscarClienteDTO(Cliente cliente) {
        BuscarClienteDTO clienteDTO = clienteMapper.fromEntityToBuscarDto(cliente);
        return clienteDTO;
    }

}
