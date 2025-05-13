package salao.online.application.services.interfaces;

import java.util.List;
import java.util.UUID;

import salao.online.application.dtos.dtosDeProduto.CriarProdutoDTO;
import salao.online.application.dtos.dtosDeProduto.ProdutoDTO;
import salao.online.domain.exceptions.ValidacaoException;

public interface ProdutoService {

    CriarProdutoDTO cadastrarProduto(CriarProdutoDTO produtoDTO) throws ValidacaoException;

    ProdutoDTO atualizarProduto(ProdutoDTO produtoDTO) throws ValidacaoException;

    ProdutoDTO buscarProdutoPorId(UUID idProduto) throws ValidacaoException;

    List<ProdutoDTO> listarProdutosDoEstoque(UUID idEstoque);

    void deletarProduto(UUID idProduto) throws ValidacaoException;
}
