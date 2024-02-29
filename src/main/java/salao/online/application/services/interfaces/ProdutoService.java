package salao.online.application.services.interfaces;

import java.util.List;
import java.util.UUID;

import salao.online.application.dtos.ProdutoDTO;
import salao.online.domain.exceptions.ValidacaoException;

public interface ProdutoService {

    public ProdutoDTO inserirProduto(ProdutoDTO servicoDTO) throws ValidacaoException;

    public ProdutoDTO atualizarProduto(ProdutoDTO servicoDTO) throws ValidacaoException;

    public ProdutoDTO buscarProdutoPorId(UUID idProduto) throws ValidacaoException;

    public List<ProdutoDTO> buscarProdutosDoEstoque(UUID idEstoque);
}

