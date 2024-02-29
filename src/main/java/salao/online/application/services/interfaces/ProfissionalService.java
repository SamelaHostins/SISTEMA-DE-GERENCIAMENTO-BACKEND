package salao.online.application.services.interfaces;

import java.util.UUID;

import salao.online.application.dtos.ProfissionalDTO;
import salao.online.domain.exceptions.ValidacaoException;

public interface ProfissionalService {

    public ProfissionalDTO inserirProfissional(ProfissionalDTO profissionalDTO);

    public ProfissionalDTO atualizarProfissional(ProfissionalDTO profissionalDTO) throws ValidacaoException;

    public ProfissionalDTO buscarProfissionalPorId(UUID idProfissional) throws ValidacaoException;
}
