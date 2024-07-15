package salao.online.application.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import salao.online.application.dtos.TipoServicoEnumDTO;
import salao.online.application.dtos.dtosDoServico.AtualizarServicoDTO;
import salao.online.application.dtos.dtosDoServico.CriarServicoDTO;
import salao.online.application.dtos.dtosDoServico.ServicoDTO;
import salao.online.application.mappers.AgendamentoMapper;
import salao.online.application.mappers.AvaliacaoMapper;
import salao.online.application.mappers.ProdutoMapper;
import salao.online.application.mappers.ProfissionalMapper;
import salao.online.application.mappers.ServicoMapper;
import salao.online.application.mappers.TipoServicoEnumMapper;
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
    TipoServicoEnumMapper tipoServicoMapper;

    @Inject
    ServicoRepository servicoRepository;

    @Inject
    ProfissionalRepository profissionalRepository;

    private static Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    @Override
    public CriarServicoDTO cadastrarServico(CriarServicoDTO servicoDTO) {
        Servico servico = servicoMapper.criarDtoToEntity(servicoDTO);
        logger.info("Salvando o servico criado");
        servicoRepository.persistAndFlush(servico);
        return servicoMapper.toDtoCriar(servico);
    }

    @Override
    public AtualizarServicoDTO atualizarServico(AtualizarServicoDTO servicoDTO) throws ValidacaoException {
        Optional<Servico> servicoOptional = servicoRepository.findByIdOptional(servicoDTO.getIdServico());
        if (servicoOptional.isPresent()) {
            Servico servico = servicoOptional.get();
            TipoServicoEnum tipoServico = tipoServicoMapper.toEntity(servicoDTO.getTipoServico());
            servico.atualizarCadastroServico(tipoServico, servicoDTO.getNome(), servicoDTO.getEspecificacao(),
                    servicoDTO.getTermosECondicoes(), servicoDTO.getValor());
            logger.info("Salvando registro atualizado");
            servicoRepository.persistAndFlush(servico);
            return servicoMapper.toDtoAtualizar(servico);
        } else {
            throw new ValidacaoException(MensagemErroValidacaoEnum.SERVICO_NAO_ENCONTRADO.getMensagemErro());
        }
    }

    @Override
    public void deletarCadastroServico(UUID idServico) throws ValidacaoException {
        logger.info("Validando se o serviço existe");
        Servico servico = servicoRepository.findById(idServico);
        if (servico != null) {
            servicoRepository.deletarServico(idServico);
        }
        throw new ValidacaoException("O serviço fornecido não existe.");
    }

    @Override
    public List<ServicoDTO> listarServicosDoProfissional(UUID idProfissional, TipoServicoEnumDTO tipoServicoDTO)
            throws ValidacaoException {
        logger.info("Obtendo informações para listar os serviços");
        Profissional profissional = profissionalRepository.findById(idProfissional);
        if (profissional.getIdProfissional() == null) {
            throw new ValidacaoException(MensagemErroValidacaoEnum.PROFISSIONAL_NAO_ENCONTRADO.getMensagemErro());
        } else {
            TipoServicoEnum tipoServico = tipoServicoMapper.toEntity(tipoServicoDTO);
            List<Servico> servicos = profissionalRepository
                    .buscarServicosDoProfissional(profissional.getIdProfissional(), tipoServico);
            return servicos.stream()
                    .map(servico -> getServicoDTO(servico))
                    .collect(Collectors.toList());
        }
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

    private ServicoDTO getServicoDTO(Servico servico) {
        ServicoDTO servicoDTO = servicoMapper.toDto(servico);
        servicoDTO.setIdProfissional(profissionalMapper.toDto(servico.getProfissional()).getIdProfissional());
        servicoDTO.setAvaliacoes(avaliacaoMapper.toDtoList(servico.getAvaliacoes()));
        servicoDTO.setAgendamentos(agendamentoMapper.toDtoList(servico.getAgendamentos()));
        return servicoDTO;
    }
}
