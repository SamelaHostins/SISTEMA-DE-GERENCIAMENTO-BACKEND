package salao.online.application.services.interfaces;

import java.util.UUID;

import salao.online.application.dtos.dtosDoProfissional.BuscarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.CriarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.ListarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.ProfissionalDTO;
import salao.online.domain.exceptions.ValidacaoException;

public interface ProfissionalService {

    public CriarProfissionalDTO cadastrarProfissional(CriarProfissionalDTO profissionalDTO);

    public ProfissionalDTO atualizarProfissional(ProfissionalDTO profissionalDTO) throws ValidacaoException;

    public BuscarProfissionalDTO buscarProfissionalPorId(UUID idProfissional) throws ValidacaoException;

    public ListarProfissionalDTO listarProfissionalPorId(UUID idProfissional) throws ValidacaoException;

    public void deletarProfissional(UUID idProfissional) throws ValidacaoException;
}
