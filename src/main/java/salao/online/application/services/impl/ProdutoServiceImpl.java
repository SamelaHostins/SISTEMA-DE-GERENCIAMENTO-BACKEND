package salao.online.application.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import salao.online.application.dtos.dtosDeProduto.CriarProdutoDTO;
import salao.online.application.dtos.dtosDeProduto.ProdutoDTO;
import salao.online.application.mappers.EstoqueMapper;
import salao.online.application.mappers.ProdutoMapper;
import salao.online.application.services.interfaces.ProdutoService;
import salao.online.domain.entities.Produto;
import salao.online.domain.enums.MensagemErroValidacaoEnum;
import salao.online.domain.exceptions.ValidacaoException;
import salao.online.infra.repositories.EstoqueRepository;
import salao.online.infra.repositories.ProdutoRepository;

@ApplicationScoped
public class ProdutoServiceImpl implements ProdutoService {

    @Inject
    ProdutoMapper produtoMapper;

    @Inject
    EstoqueMapper estoqueMapper;

    @Inject
    ProdutoRepository produtoRepository;

    @Inject
    EstoqueRepository estoqueRepository;

    private static Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    @Override
    public CriarProdutoDTO cadastrarProduto(CriarProdutoDTO produtoDTO) throws ValidacaoException {
        Produto produto = produtoMapper.criarDtoToEntity(produtoDTO);
        logger.info("Salvando o produto criado");
        produtoRepository.persistAndFlush(produto);
        return produtoMapper.toCriarDto(produto);
    }

    // não está pronto ainda
    @Override
    public ProdutoDTO atualizarProduto(ProdutoDTO produtoDTO) throws ValidacaoException {
        throw new UnsupportedOperationException("Unimplemented method 'atualizarProduto'");
    }

    @Override
    public ProdutoDTO buscarProdutoPorId(UUID idProduto) throws ValidacaoException {
        logger.info("Validando se o Produto existe");
        Produto produto = produtoRepository.findByIdOptional(idProduto)
                .orElseThrow(() -> new ValidacaoException(
                        MensagemErroValidacaoEnum.PRODUTO_NAO_ENCONTRADO.getMensagemErro()));
        return getProdutoDTO(produto);
    }

    @Override
    public void deletarProduto(UUID idProduto) throws ValidacaoException {
        logger.info("Validando se o produto existe");
        buscarProdutoPorId(idProduto);
        produtoRepository.deletarProduto(idProduto);
    }

    @Override
    public List<ProdutoDTO> listarProdutosDoEstoque(UUID idEstoque) {
        List<Produto> produtos = estoqueRepository.listarProdutosDoEstoque(idEstoque);
        return produtos.stream()
                .map(produto -> getProdutoDTO(produto))
                .collect(Collectors.toList());
    }

    private ProdutoDTO getProdutoDTO(Produto produto) {
        ProdutoDTO produtoDTO = produtoMapper.toDto(produto);
        produtoDTO.setIdEstoque(estoqueMapper.toDto(produto.getEstoque()).getIdEstoque());
        return produtoDTO;
    }
}
