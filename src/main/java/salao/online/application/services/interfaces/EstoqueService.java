package salao.online.application.services.interfaces;

import java.util.List;
import java.util.UUID;

import salao.online.application.dtos.EstoqueDTO;
import salao.online.domain.exceptions.ValidacaoException;

public interface EstoqueService {

    public EstoqueDTO inserirEstoque(EstoqueDTO servicoDTO);

    public EstoqueDTO atualizarEstoque(EstoqueDTO servicoDTO) throws ValidacaoException;

    public EstoqueDTO buscarEstoquePorId(UUID idEstoque) throws ValidacaoException;

    public List<EstoqueDTO> buscarEstoquesDoProfissional(UUID idProfissional);
}

