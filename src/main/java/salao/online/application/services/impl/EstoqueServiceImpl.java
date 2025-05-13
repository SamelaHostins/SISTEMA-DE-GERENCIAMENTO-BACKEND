package salao.online.application.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import salao.online.application.dtos.dtosDoEstoque.CriarEstoqueDTO;
import salao.online.application.dtos.dtosDoEstoque.EstoqueDTO;
import salao.online.application.mappers.EstoqueMapper;
import salao.online.application.mappers.ProdutoMapper;
import salao.online.application.mappers.ProfissionalMapper;
import salao.online.application.services.interfaces.EstoqueService;
import salao.online.domain.entities.Estoque;
import salao.online.domain.entities.Profissional;
import salao.online.domain.enums.MensagemErroValidacaoEnum;
import salao.online.domain.exceptions.ValidacaoException;
import salao.online.infra.repositories.EstoqueRepository;
import salao.online.infra.repositories.ProfissionalRepository;

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

    @Inject
    ProfissionalRepository profissionalRepository;

    private static Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    @Override
    public CriarEstoqueDTO cadastrarEstoque(CriarEstoqueDTO estoqueDTO) {
        Estoque estoque = estoqueMapper.fromCriarDtoToEntity(estoqueDTO);
        logger.info("Salvando o estoque criado");
        estoqueRepository.persistAndFlush(estoque);
        return estoqueMapper.fromEntityToCriarDto(estoque);
    }

    @Override
    public EstoqueDTO atualizarEstoque(EstoqueDTO estoqueDTO) throws ValidacaoException {
        throw new UnsupportedOperationException("Unimplemented method 'atualizarEstoque'");
    }

    @Override
    public EstoqueDTO buscarEstoquePorId(UUID idEstoque) throws ValidacaoException {
        logger.info("Validando se o Estoque existe");
        Estoque estoque = estoqueRepository.findByIdOptional(idEstoque)
                .orElseThrow(() -> new ValidacaoException(
                        MensagemErroValidacaoEnum.ESTOQUE_NAO_ENCONTRADO.getMensagemErro()));
        return getEstoqueDTO(estoque);
    }

    @Override
    public List<EstoqueDTO> buscarEstoquesDoProfissional(UUID idProfissional) throws ValidacaoException {
        logger.info("Verificando se o profissional existe");
        Profissional profissional = profissionalRepository.findById(idProfissional);
        if (profissional.getIdProfissional() == null) {
            throw new ValidacaoException(MensagemErroValidacaoEnum.PROFISSIONAL_NAO_ENCONTRADO.getMensagemErro());
        } else {
            List<Estoque> estoques = profissionalRepository
                    .buscarEstoquesDoProfissional(profissional.getIdProfissional());
            return estoques.stream()
                    .map(estoque -> getEstoqueDTO(estoque))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void deletarEstoque(UUID idEstoque, UUID idProfissional) {
        Estoque estoque = estoqueRepository.findById(idEstoque);

        if (estoque == null) {
            throw new WebApplicationException("Estoque não encontrado", 404);
        }

        if (!estoque.getProfissional().getIdProfissional().equals(idProfissional)) {
            throw new WebApplicationException("Acesso negado: estoque não pertence ao profissional", 403);
        }

        estoqueRepository.delete(estoque); // Produtos serão apagados via cascade
    }

    private EstoqueDTO getEstoqueDTO(Estoque estoque) {
        EstoqueDTO estoqueDTO = estoqueMapper.fromEntityToDto(estoque);
        estoqueDTO.setIdProfissional(
                profissionalMapper.fromEntityToBuscarDto(estoque.getProfissional()).getIdProfissional());
        estoqueDTO.setProdutos(produtoMapper.fromEntityListToDtoList(estoque.getProdutos()));
        return estoqueDTO;
    }

}
