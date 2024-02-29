package salao.online.application.services.impl;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import salao.online.application.dtos.ProdutoDTO;
import salao.online.application.mappers.EstoqueMapper;
import salao.online.application.mappers.ProdutoMapper;
import salao.online.application.services.interfaces.ProdutoService;
import salao.online.domain.entities.Produto;
import salao.online.domain.enums.MensagemErroValidacaoEnum;
import salao.online.domain.exceptions.ValidacaoException;
import salao.online.domain.repositories.ProdutoRepository;

@ApplicationScoped
public class ProdutoServiceImpl implements ProdutoService {

    @Inject
    ProdutoMapper produtoMapper;

    @Inject
    EstoqueMapper estoqueMapper;

    @Inject
    ProdutoRepository produtoRepository;

    private static Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    @Override
    public ProdutoDTO inserirProduto(ProdutoDTO produtoDTO) throws ValidacaoException {
        Produto produto = produtoMapper.toEntity(produtoDTO);
        logger.info("Salvando o produto criado");
        produtoRepository.persistAndFlush(produto);
        return getProdutoDTO(produto);
    }

    @Override
    public ProdutoDTO atualizarProduto(ProdutoDTO produtoDTO) throws ValidacaoException {
        // TODO Auto-generated method stub
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
    public List<ProdutoDTO> buscarProdutosDoEstoque(UUID idProduto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarProdutosDoProduto'");
    }

    private ProdutoDTO getProdutoDTO(Produto produto) {
        ProdutoDTO produtoDTO = produtoMapper.toDto(produto);
        produtoDTO.setIdEstoque(estoqueMapper.toDto(produto.getEstoque()).getIdEstoque());
        return produtoDTO;
    }

}
