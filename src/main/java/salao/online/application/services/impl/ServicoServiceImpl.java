package salao.online.application.services.impl;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import salao.online.application.dtos.ServicoDTO;
import salao.online.application.mappers.AgendamentoMapper;
import salao.online.application.mappers.AvaliacaoMapper;
import salao.online.application.mappers.ProfissionalMapper;
import salao.online.application.mappers.ServicoMapper;
import salao.online.application.services.interfaces.ServicoService;
import salao.online.domain.entities.Servico;
import salao.online.domain.enums.MensagemErroValidacaoEnum;
import salao.online.domain.exceptions.ValidacaoException;
import salao.online.infra.repositories.ServicoRepository;

@ApplicationScoped
public class ServicoServiceImpl implements ServicoService {

    @Inject
    ServicoMapper servicoMapper;

    @Inject
    AvaliacaoMapper avaliacaoMapper;

    @Inject
    ProfissionalMapper profissionalMapper;

    @Inject
    AgendamentoMapper agendamentoMapper;

    @Inject
    ServicoRepository servicoRepository;

    private static Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    @Override
    public ServicoDTO inserirServico(ServicoDTO servicoDTO) {
        Servico servico = servicoMapper.toEntity(servicoDTO);
        logger.info("Salvando o servico criado");
        servicoRepository.persistAndFlush(servico);
        return getServicoDTO(servico);
    }

    @Override
    public ServicoDTO atualizarServico(ServicoDTO servicoDTO) throws ValidacaoException {
        throw new UnsupportedOperationException("Unimplemented method 'atualizarServico'");
    }

    @Override
    public ServicoDTO buscarServicoPorId(UUID idServico) throws ValidacaoException {
        logger.info("Validando se o Servico existe");
        Servico servico = servicoRepository.findByIdOptional(idServico)
                .orElseThrow(() -> new ValidacaoException(
                        MensagemErroValidacaoEnum.SERVICO_NAO_ENCONTRADO.getMensagemErro()));
        return getServicoDTO(servico);
    }

    private ServicoDTO getServicoDTO(Servico servico) {
        ServicoDTO servicoDTO = servicoMapper.toDto(servico);
        servicoDTO.setIdProfissional(profissionalMapper.toDto(servico.getProfissional()).getIdProfissional());
        servicoDTO.setAvaliacoes(avaliacaoMapper.toDtoList(servico.getAvaliacoes()));
        servicoDTO.setAgendamentos(agendamentoMapper.toDtoList(servico.getAgendamentos()));
        return servicoDTO;
    }
}
