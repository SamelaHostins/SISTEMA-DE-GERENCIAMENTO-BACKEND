package salao.online.application.services.interfaces;

import java.util.List;
import java.util.UUID;

import salao.online.application.dtos.dtosDoEstoque.CriarEstoqueDTO;
import salao.online.application.dtos.dtosDoEstoque.EstoqueDTO;
import salao.online.domain.exceptions.ValidacaoException;

public interface EstoqueService {

    public CriarEstoqueDTO cadastrarEstoque(CriarEstoqueDTO servicoDTO);

    public EstoqueDTO atualizarEstoque(EstoqueDTO servicoDTO) throws ValidacaoException;

    public EstoqueDTO buscarEstoquePorId(UUID idEstoque) throws ValidacaoException;

    public List<EstoqueDTO> buscarEstoquesDoProfissional(UUID idProfissional) throws ValidacaoException;
}

