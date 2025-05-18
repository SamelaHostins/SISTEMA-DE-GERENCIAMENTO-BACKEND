package salao.online.application.services.interfaces;

import java.util.List;
import java.util.UUID;

import salao.online.application.dtos.dtosDoEstoque.CriarEstoqueDTO;
import salao.online.application.dtos.dtosDoEstoque.EstoqueDTO;
import salao.online.domain.exceptions.ValidacaoException;

public interface EstoqueService {

    CriarEstoqueDTO cadastrarEstoque(CriarEstoqueDTO servicoDTO) throws ValidacaoException;

    EstoqueDTO atualizarEstoque(EstoqueDTO dto, UUID idProfissional) throws ValidacaoException;

    EstoqueDTO buscarEstoquePorId(UUID idEstoque) throws ValidacaoException;

    List<EstoqueDTO> buscarEstoquesDoProfissional(UUID idProfissional) throws ValidacaoException;

    void deletarEstoque(UUID idEstoque, UUID idProfissional);
}
