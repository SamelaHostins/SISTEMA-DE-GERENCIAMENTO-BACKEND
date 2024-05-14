package salao.online.application.services.impl;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import salao.online.application.dtos.ProfissionalDTO;
import salao.online.application.mappers.EnderecoMapper;
import salao.online.application.mappers.EstoqueMapper;
import salao.online.application.mappers.ProfissionalMapper;
import salao.online.application.mappers.ServicoMapper;
import salao.online.application.services.interfaces.ProfissionalService;
import salao.online.domain.entities.Profissional;
import salao.online.domain.enums.MensagemErroValidacaoEnum;
import salao.online.domain.exceptions.ValidacaoException;
import salao.online.domain.repositories.ProfissionalRepository;

@ApplicationScoped
public class ProfissionalServiceImpl implements ProfissionalService {

    @Inject
    ProfissionalMapper profissionalMapper;

    @Inject
    ServicoMapper servicoMapper;

    @Inject
    EnderecoMapper enderecoMapper;

    @Inject
    EstoqueMapper estoqueMapper;

    @Inject
    ProfissionalRepository profissionalRepository;

    private static Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    @Override
    @Transactional
    public ProfissionalDTO inserirProfissional(ProfissionalDTO profissionalDTO) {
        Profissional profissional = profissionalMapper.toEntity(profissionalDTO);
        logger.info("Salvando o profissional criado");
        profissionalRepository.persistAndFlush(profissional);
        return getProfissionalDTO(profissional);
    }

    @Override
    public ProfissionalDTO atualizarProfissional(ProfissionalDTO profissionalDTO) throws ValidacaoException {
        throw new UnsupportedOperationException("Unimplemented method 'atualizarProfissional'");
    }

    @Override
    public ProfissionalDTO buscarProfissionalPorId(UUID idProfissional) throws ValidacaoException {
        logger.info("Validando se o Profissional existe");
        Profissional profissional = profissionalRepository.findByIdOptional(idProfissional)
                .orElseThrow(() -> new ValidacaoException(
                        MensagemErroValidacaoEnum.PROFISSIONAL_NAO_ENCONTRADO.getMensagemErro()));
        return getProfissionalDTO(profissional);
    }

    private ProfissionalDTO getProfissionalDTO(Profissional profissional) {
        ProfissionalDTO profissionalDTO = profissionalMapper.toDto(profissional);
        profissionalDTO.setIdEndereco(enderecoMapper.toDto(profissional.getEndereco()).getIdEndereco());
        profissionalDTO.setServicos(servicoMapper.toDtoList(profissional.getServicos()));
        profissionalDTO.setEstoques(estoqueMapper.toDtoList(profissional.getEstoques()));
        return profissionalDTO;
    }

}
