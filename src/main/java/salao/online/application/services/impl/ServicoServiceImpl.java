package salao.online.application.services.impl;

import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import salao.online.application.dtos.dtosDoServico.AtualizarServicoDTO;
import salao.online.application.dtos.dtosDoServico.CriarServicoDTO;
import salao.online.application.dtos.dtosDoServico.ServicoDTO;
import salao.online.application.dtos.dtosDoServico.TipoServicoEnumDTO;
import salao.online.application.dtos.dtosParaPesquisar.PesquisaLocalDTO;
import salao.online.application.dtos.dtosParaPesquisar.PesquisaServicoDTO;
import salao.online.application.mappers.AgendamentoMapper;
import salao.online.application.mappers.AvaliacaoMapper;
import salao.online.application.mappers.LocalMapper;
import salao.online.application.mappers.ProdutoMapper;
import salao.online.application.mappers.ProfissionalMapper;
import salao.online.application.mappers.ServicoMapper;
import salao.online.application.services.interfaces.ServicoService;
import salao.online.domain.entities.Profissional;
import salao.online.domain.entities.Servico;
import salao.online.domain.enums.MensagemErroValidacaoEnum;
import salao.online.domain.enums.TipoServicoEnum;
import salao.online.domain.exceptions.ValidacaoException;
import salao.online.infra.repositories.ProfissionalRepository;
import salao.online.infra.repositories.ServicoRepository;

@ApplicationScoped
public class ServicoServiceImpl implements ServicoService {

    @Inject
    ServicoMapper servicoMapper;

    @Inject
    AvaliacaoMapper avaliacaoMapper;

    @Inject
    ProdutoMapper produtoMapper;

    @Inject
    ProfissionalMapper profissionalMapper;

    @Inject
    AgendamentoMapper agendamentoMapper;

    @Inject
    LocalMapper localMapper;

    @Inject
    ServicoRepository servicoRepository;

    @Inject
    ProfissionalRepository profissionalRepository;

    private static Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    @Override
    public CriarServicoDTO cadastrarServico(CriarServicoDTO servicoDTO) throws ValidacaoException {
        Profissional profissional = profissionalRepository.findById(servicoDTO.getIdProfissional());
        if (profissional == null) {
            throw new ValidacaoException(MensagemErroValidacaoEnum.PROFISSIONAL_NAO_ENCONTRADO.getMensagemErro());
        }

        Servico servico = servicoMapper.fromCriarDtoToEntity(servicoDTO);
        servico.setProfissional(profissional);

        logger.info("Salvando o servico criado");
        servicoRepository.persistAndFlush(servico);

        return servicoMapper.fromEntityToCriarDto(servico);
    }

    @Override
    public AtualizarServicoDTO atualizarServico(AtualizarServicoDTO servicoDTO) throws ValidacaoException {
        Servico servico = servicoRepository.findByIdOptional(servicoDTO.getIdServico())
                .orElseThrow(() -> new ValidacaoException(
                        MensagemErroValidacaoEnum.SERVICO_NAO_ENCONTRADO.getMensagemErro()));

        // Se tipoServico for nulo, mantém o valor atual do serviço
        TipoServicoEnum tipoServico = (servicoDTO.getTipoServico() != null)
                ? TipoServicoEnum.valueOf(servicoDTO.getTipoServico().name())
                : servico.getTipoServico();

        Duration tempoDuration;
        try {
            tempoDuration = Duration.parse("PT" + servicoDTO.getTempo().replace(":", "H") + "M");
        } catch (DateTimeParseException e) {
            throw new ValidacaoException("Formato de tempo inválido. Use o formato hh:mm");
        }

        servico.atualizarCadastroServico(tipoServico,
                servicoDTO.getNome(),
                servicoDTO.getEspecificacao(),
                servicoDTO.getTermosECondicoes(),
                tempoDuration,
                servicoDTO.getValor());

        logger.info("Salvando registro atualizado");
        servicoRepository.persistAndFlush(servico);

        return servicoMapper.fromEntityToAtualizarDto(servico);
    }

    @Override
    public boolean possuiAgendamentos(UUID idServico) throws ValidacaoException {
        Servico servico = servicoRepository.findById(idServico);
        if (servico == null) {
            throw new ValidacaoException(MensagemErroValidacaoEnum.SERVICO_NAO_ENCONTRADO.getMensagemErro());
        }
        return servico.getAgendamentos() != null && !servico.getAgendamentos().isEmpty();
    }

    @Override
    public void deletarCadastroServico(UUID idServico) throws ValidacaoException {
        Servico servico = servicoRepository.findById(idServico);
        if (servico == null) {
            throw new ValidacaoException(MensagemErroValidacaoEnum.SERVICO_NAO_ENCONTRADO.getMensagemErro());
        }
        servicoRepository.delete(servico);
    }

    @Override
    public List<ServicoDTO> listarServicosDoProfissional(UUID idProfissional, int tipoServico)
            throws ValidacaoException {
        logger.info("Obtendo informações para listar os serviços");
        Profissional profissional = profissionalRepository.findById(idProfissional);
        if (profissional == null || profissional.getIdProfissional() == null) {
            throw new ValidacaoException(MensagemErroValidacaoEnum.PROFISSIONAL_NAO_ENCONTRADO.getMensagemErro());
        }

        TipoServicoEnumDTO tipoServicoDTO = TipoServicoEnumDTO.fromTipoServico(tipoServico);
        TipoServicoEnum tipoServicoEnum = TipoServicoEnum.valueOf(tipoServicoDTO.name());

        List<Servico> servicos = profissionalRepository.buscarServicosDoProfissional(profissional.getIdProfissional(),
                tipoServicoEnum);

        return servicos.stream()
                .map(this::getServicoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServicoDTO> listarTodosOsServicosDoProfissional(UUID idProfissional)
            throws ValidacaoException {
        logger.info("Obtendo informações para listar os serviços");
        Profissional profissional = profissionalRepository.findById(idProfissional);
        if (profissional.getIdProfissional() == null) {
            throw new ValidacaoException(MensagemErroValidacaoEnum.PROFISSIONAL_NAO_ENCONTRADO.getMensagemErro());
        } else {
            List<Servico> servicos = profissionalRepository
                    .buscarTodosOsServicosDoProfissional(profissional.getIdProfissional());
            return servicos.stream()
                    .map(servico -> getServicoDTO(servico))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<PesquisaServicoDTO> pesquisarTodosServicos() {
        List<Servico> servicos = servicoRepository.findAll().list();
        return servicoMapper.fromEntityListToPesquisaDtoList(servicos);
    }

    @Override
    public List<PesquisaLocalDTO> pesquisarTodasAsCidadesComServicos() {
        // 1) pega todos os serviços
        List<Servico> servicos = servicoRepository.findAll().list();

        // 2) pega o conjunto de IDs de profissionais que têm foto de perfil
        Set<UUID> profComPerfil = profissionalRepository
                .pesquisarTodosComImagemDePerfil()
                .stream()
                .map(Profissional::getIdProfissional)
                .collect(Collectors.toSet());

        // 3) Para cada serviço, filtra apenas os de profissionais com foto de perfil,
        Set<UUID> vistos = new HashSet<>();
        return servicos.stream()
                .filter(s -> profComPerfil.contains(s.getProfissional().getIdProfissional()))
                .map(localMapper::fromEntityToPesquisaLocalDto)
                .filter(dto -> vistos.add(dto.getIdProfissional()))
                .collect(Collectors.toList());
    }

    @Override
    public ServicoDTO buscarServicoPorId(UUID idServico) throws ValidacaoException {
        Servico servico = servicoRepository.findById(idServico);
        if (servico == null) {
            throw new ValidacaoException(MensagemErroValidacaoEnum.SERVICO_NAO_ENCONTRADO.getMensagemErro());
        }
        return servicoMapper.fromEntityToDto(servico);
    }

    private ServicoDTO getServicoDTO(Servico servico) {
        ServicoDTO servicoDTO = servicoMapper.fromEntityToDto(servico);
        servicoDTO.setIdProfissional(
                profissionalMapper.fromEntityToListarDto(servico.getProfissional()).getIdProfissional());
        servicoDTO.setAvaliacoes(avaliacaoMapper.fromEntityListToDtoList(servico.getAvaliacoes()));
        servicoDTO.setAgendamentos(agendamentoMapper.fromEntityListToDtoList(servico.getAgendamentos()));
        return servicoDTO;
    }
}
