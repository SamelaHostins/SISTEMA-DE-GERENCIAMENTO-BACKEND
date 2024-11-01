package salao.online.application.services.impl;

import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import salao.online.application.dtos.dtosDoProfissional.BuscarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.CriarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.ListarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.ProfissionalDTO;
import salao.online.application.mappers.EstoqueMapper;
import salao.online.application.mappers.ProfissionalMapper;
import salao.online.application.mappers.ServicoMapper;
import salao.online.application.services.interfaces.ProfissionalService;
import salao.online.domain.entities.Profissional;
import salao.online.domain.enums.MensagemErroValidacaoEnum;
import salao.online.domain.exceptions.ValidacaoException;
import salao.online.infra.repositories.ProfissionalRepository;

@ApplicationScoped
public class ProfissionalServiceImpl implements ProfissionalService {

    @Inject
    ProfissionalMapper profissionalMapper;

    @Inject
    ServicoMapper servicoMapper;

    @Inject
    EstoqueMapper estoqueMapper;

    @Inject
    ProfissionalRepository profissionalRepository;

    private static Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    @Override
    @Transactional
    public CriarProfissionalDTO cadastrarProfissional(CriarProfissionalDTO profissionalDTO) {
        Profissional profissional = profissionalMapper.criarDtoToEntity(profissionalDTO);
        logger.info("Salvando o profissional criado");
        profissionalRepository.persistAndFlush(profissional);
        return profissionalMapper.toDtoCriar(profissional);
    }

    @Override
    public ProfissionalDTO atualizarProfissional(ProfissionalDTO profissionalDTO) throws ValidacaoException {
        throw new UnsupportedOperationException("Unimplemented method 'atualizarProfissional'");
    }

    @Override
    public BuscarProfissionalDTO buscarProfissionalPorId(UUID idProfissional) throws ValidacaoException {
        logger.info("Validando se o Profissional existe");
        Profissional profissional = profissionalRepository.findByIdOptional(idProfissional)
                .orElseThrow(() -> new ValidacaoException(
                        MensagemErroValidacaoEnum.PROFISSIONAL_NAO_ENCONTRADO.getMensagemErro()));
        return getBuscarProfissionalDTO(profissional);
    }

    @Override
    public ListarProfissionalDTO listarProfissionalPorId(UUID idProfissional) throws ValidacaoException {
        logger.info("Validando se o Profissional existe");
        Profissional profissional = profissionalRepository.findByIdOptional(idProfissional)
                .orElseThrow(() -> new ValidacaoException(
                        MensagemErroValidacaoEnum.PROFISSIONAL_NAO_ENCONTRADO.getMensagemErro()));
        return getListarProfissionalDTO(profissional);
    }

    @Override
    public void deletarProfissional(UUID idProfissional) throws ValidacaoException {
        logger.info("Validando se o Cliente existe");
        buscarProfissionalPorId(idProfissional);
        profissionalRepository.deletarProfissional(idProfissional);
    }

    private BuscarProfissionalDTO getBuscarProfissionalDTO(Profissional profissional) {
        BuscarProfissionalDTO profissionalDTO = profissionalMapper.toDtoBuscar(profissional);
        profissionalDTO.setServicos(servicoMapper.toDtoList(profissional.getServicos()));
        profissionalDTO.setEstoques(estoqueMapper.toDtoList(profissional.getEstoques()));
        return profissionalDTO;
    }

    private ListarProfissionalDTO getListarProfissionalDTO(Profissional profissional) {
        ListarProfissionalDTO profissionalDTO = profissionalMapper.toDtoListar(profissional);
        profissionalDTO.setServicos(servicoMapper.toDtoList(profissional.getServicos()));
        return profissionalDTO;
    }

}
