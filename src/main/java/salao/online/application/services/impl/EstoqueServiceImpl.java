package salao.online.application.services.impl;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import salao.online.application.dtos.EstoqueDTO;
import salao.online.application.mappers.EstoqueMapper;
import salao.online.application.mappers.ProdutoMapper;
import salao.online.application.mappers.ProfissionalMapper;
import salao.online.application.services.interfaces.EstoqueService;
import salao.online.domain.entities.Estoque;
import salao.online.domain.enums.MensagemErroValidacaoEnum;
import salao.online.domain.exceptions.ValidacaoException;
import salao.online.domain.repositories.EstoqueRepository;

@ApplicationScoped
public class EstoqueServiceImpl implements EstoqueService {

    @Inject
    EstoqueMapper estoqueMapper;

    @Inject
    ProdutoMapper produtoMapper;

    @Inject
    ProfissionalMapper profissionalMapper;

    @Inject
    EstoqueRepository estoqueRepository;

    private static Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    @Override
    public EstoqueDTO inserirEstoque(EstoqueDTO estoqueDTO) {
        Estoque estoque = estoqueMapper.toEntity(estoqueDTO);
        logger.info("Salvando o estoque criado");
        estoqueRepository.persistAndFlush(estoque);
        return getEstoqueDTO(estoque);
    }

    @Override
    public EstoqueDTO atualizarEstoque(EstoqueDTO estoqueDTO) throws ValidacaoException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atualizarEstoque'");
    }

    @Override
    public EstoqueDTO buscarEstoquePorId(UUID idEstoque) throws ValidacaoException {
        logger.info("Validando se o Estoque existe");
        Estoque estoque = estoqueRepository.findByIdOptional(idEstoque)
                .orElseThrow(() -> new ValidacaoException(
                        MensagemErroValidacaoEnum.PROFISSIONAL_NAO_ENCONTRADO.getMensagemErro()));
        return getEstoqueDTO(estoque);
    }

    @Override
    public List<EstoqueDTO> buscarEstoquesDoProfissional(UUID idEstoque) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarEstoquesDoEstoque'");
    }

    private EstoqueDTO getEstoqueDTO(Estoque estoque) {
        EstoqueDTO estoqueDTO = estoqueMapper.toDto(estoque);
        estoqueDTO.setIdProfissional(profissionalMapper.toDto(estoque.getProfissional()).getIdProfissional());
        estoqueDTO.setProdutos(produtoMapper.toDtoList(estoque.getProdutos()));
        return estoqueDTO;
    }

}
