package salao.online.application.services.interfaces;

import java.util.List;
import java.util.UUID;

import salao.online.application.dtos.dtosDeProduto.CriarProdutoDTO;
import salao.online.application.dtos.dtosDeProduto.ProdutoDTO;
import salao.online.domain.exceptions.ValidacaoException;

public interface ProdutoService {

    public CriarProdutoDTO cadastrarProduto(CriarProdutoDTO produtoDTO) throws ValidacaoException;

    public ProdutoDTO atualizarProduto(ProdutoDTO produtoDTO) throws ValidacaoException;

    public ProdutoDTO buscarProdutoPorId(UUID idProduto) throws ValidacaoException;

    public List<ProdutoDTO> listarProdutosDoEstoque(UUID idEstoque);

    public void deletarProduto(UUID idProduto) throws ValidacaoException;
}

